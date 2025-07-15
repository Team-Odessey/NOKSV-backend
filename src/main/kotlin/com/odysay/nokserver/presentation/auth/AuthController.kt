package com.odysay.nokserver.presentation.auth

import com.odysay.nokserver.application.auth.AuthService
import com.odysay.nokserver.presentation.auth.dto.AuthRequest
import com.odysay.nokserver.presentation.auth.dto.AuthResponse
import com.odysay.nokserver.presentation.auth.dto.RefreshTokenRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        return try {
            val response = authService.signup(request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(AuthResponse(message = e.message))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        return try {
            val response = authService.login(request)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse(message = e.message))
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<AuthResponse> {
        return try {
            val response = authService.refreshToken(request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(AuthResponse(message = e.message))
        }
    }
}
