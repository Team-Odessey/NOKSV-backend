package com.odysay.nokserver.presentation.auth.dto

data class AuthRequest(
    val username: String,
    val password: String,
    val nickname: String?
)
