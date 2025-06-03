package com.yandey.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val isSuccess: Boolean
)
