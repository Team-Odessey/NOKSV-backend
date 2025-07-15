package com.odysay.nokserver.presentation.common.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val isSuccess: Boolean = false,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String?
)
