package com.odysay.nokserver.presentation.auth.dto

data class AuthResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val message: String? = null
)
