package com.odysay.nokserver.application.auth

import com.odysay.nokserver.domain.member.Member
import com.odysay.nokserver.domain.member.MemberRepository
import com.odysay.nokserver.domain.member.RefreshToken
import com.odysay.nokserver.domain.member.RefreshTokenRepository
import com.odysay.nokserver.infrastructure.security.JwtProvider
import com.odysay.nokserver.presentation.auth.dto.AuthRequest
import com.odysay.nokserver.presentation.auth.dto.AuthResponse
import com.odysay.nokserver.presentation.auth.dto.RefreshTokenRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import com.odysay.nokserver.infrastructure.minecraft.MinecraftApiService
import java.util.UUID

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val authenticationManager: AuthenticationManager,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val minecraftApiService: MinecraftApiService
) {

    @Transactional
    fun signup(request: AuthRequest): AuthResponse {
        if (memberRepository.findByName(request.username) != null) {
            throw IllegalArgumentException("Username already exists")
        }
        if (memberRepository.findByNickname(request.nickname!!) != null) {
            throw IllegalArgumentException("Nickname already exists")
        }

        val minecraftUuid = minecraftApiService.getUuidByUsername(request.username).block()
            ?: throw IllegalArgumentException("Failed to retrieve Minecraft UUID for username: ${request.username}")

        val newMember = Member(
            name = request.username,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname!!,
            minecraftId = minecraftUuid
        )
        val savedMember = memberRepository.save(newMember)

        val accessToken = jwtProvider.generateAccessToken(savedMember.name)
        val refreshToken = jwtProvider.generateRefreshToken(savedMember.name)

        val refreshTokenEntity = RefreshToken(
            token = refreshToken,
            member = savedMember,
            expiryDate = Instant.now().plusSeconds(jwtProvider.getRefreshTokenValidityInSeconds()) // 올바른 계산
        )
        refreshTokenRepository.save(refreshTokenEntity)

        return AuthResponse(accessToken, refreshToken)
    }

    @Transactional
    fun login(request: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username!!, request.password!!)
        )

        val member = memberRepository.findByName(request.username!!)
            ?: throw UsernameNotFoundException("User not found")

        val accessToken = jwtProvider.generateAccessToken(member.name)
        val refreshToken = jwtProvider.generateRefreshToken(member.name)

        refreshTokenRepository.findByMember(member).ifPresentOrElse(
            { existingRefreshToken ->
                existingRefreshToken.token = refreshToken
                existingRefreshToken.expiryDate = Instant.now().plusSeconds(jwtProvider.getRefreshTokenValidityInSeconds()) // 올바른 계산
                refreshTokenRepository.save(existingRefreshToken)
            },
            { ->
                val newRefreshTokenEntity = RefreshToken(
                    token = refreshToken,
                    member = member,
                    expiryDate = Instant.now().plusSeconds(jwtProvider.getRefreshTokenValidityInSeconds()) // 올바른 계산
                )
                refreshTokenRepository.save(newRefreshTokenEntity)
            }
        )

        return AuthResponse(accessToken, refreshToken)
    }

    @Transactional
    fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        val storedRefreshToken = refreshTokenRepository.findByToken(request.refreshToken)
            .orElseThrow { IllegalArgumentException("Refresh token not found") }

        // JWT 내부 만료일과 DB 저장된 만료일 모두 체크
        if (jwtProvider.isTokenExpired(storedRefreshToken.token) || storedRefreshToken.expiryDate.isBefore(Instant.now())) {
            refreshTokenRepository.delete(storedRefreshToken)
            throw IllegalArgumentException("Refresh token expired or invalid")
        }

        val member = storedRefreshToken.member
        val newAccessToken = jwtProvider.generateAccessToken(member.name)
        val newRefreshToken = jwtProvider.generateRefreshToken(member.name)

        storedRefreshToken.token = newRefreshToken
        storedRefreshToken.expiryDate = Instant.now().plusSeconds(jwtProvider.getRefreshTokenValidityInSeconds()) // 올바른 계산
        refreshTokenRepository.save(storedRefreshToken)

        return AuthResponse(newAccessToken, newRefreshToken)
    }
}