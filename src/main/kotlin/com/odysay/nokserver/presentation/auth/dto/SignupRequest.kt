package com.odysay.nokserver.presentation.auth.dto

data class SignupRequest(
    val username: String,
    val password: String,
    val nickname: String
)
