package com.kmp.feature.auth.data.remote

import com.kmp.feature.auth.data.remote.dto.LoginResponseDto

interface AuthApi {
    suspend fun login(email: String, password: String): LoginResponseDto
}

