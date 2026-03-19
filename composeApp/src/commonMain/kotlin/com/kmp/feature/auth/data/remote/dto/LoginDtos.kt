package com.kmp.feature.auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponseDto(
    val user: User,
)

@Serializable
data class User(
    @SerialName("user_name")
    val userName: String,
    val email: String,
)

