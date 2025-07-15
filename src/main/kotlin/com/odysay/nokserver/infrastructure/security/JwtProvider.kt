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

    @Value("\${jwt.secret}")
    private lateinit var secret: String
    @Value("\${jwt.access-token-validity-in-seconds}")
    private var accessTokenValidityInSeconds: Long = 0
    @Value("\${jwt.refresh-token-validity-in-seconds}")
    private var refreshTokenValidityInSeconds: Long = 0

    private val key: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) }

    fun generateAccessToken(serviceUsername: String): String {
        return generateToken(serviceUsername, accessTokenValidityInSeconds * 1000)
    }

    fun generateRefreshToken(serviceUsername: String): String {
        return generateToken(serviceUsername, refreshTokenValidityInSeconds * 1000)
    }

    private fun generateToken(serviceUsername: String, expirationTime: Long): String {
        return Jwts.builder()
            .setSubject(serviceUsername)
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

    fun getRefreshTokenValidityInSeconds(): Long {
        return refreshTokenValidityInSeconds
    }
}