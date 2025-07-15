package com.odysay.nokserver.infrastructure.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import java.time.Instant // Instant 임포트 추가

@Component
class JwtProvider {

    // 문제 발생 부분: @Value 어노테이션의 문자열 리터럴 방식 변경
    @Value("\${jwt.secret}") // 큰따옴표 안에 직접 SpEL 표현식 사용
    private lateinit var secret: String
    @Value("\${jwt.access-token-validity-in-seconds}") // 큰따옴표 안에 직접 SpEL 표현식 사용
    private var accessTokenValidityInSeconds: Long = 0
    @Value("\${jwt.refresh-token-validity-in-seconds}") // 큰따옴표 안에 직접 SpEL 표현식 사용
    private var refreshTokenValidityInSeconds: Long = 0

    private val key: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) }

    fun generateAccessToken(username: String): String {
        return generateToken(username, accessTokenValidityInSeconds * 1000)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, refreshTokenValidityInSeconds * 1000)
    }

    private fun generateToken(username: String, expirationTime: Long): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun getExpirationDateFromToken(token: String): Date {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.expiration
    }

    fun isTokenExpired(token: String): Boolean {
        return getExpirationDateFromToken(token).before(Date())
    }

    // Refresh Token 유효 기간을 초 단위로 반환하는 메서드 추가
    fun getRefreshTokenValidityInSeconds(): Long {
        return refreshTokenValidityInSeconds
    }
}