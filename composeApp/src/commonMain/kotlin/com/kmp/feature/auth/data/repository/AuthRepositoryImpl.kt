package com.kmp.feature.auth.data.repository

import com.kmp.feature.auth.data.remote.AuthApi
import com.kmp.feature.auth.domain.model.UserProfile
import com.kmp.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {
    override suspend fun login(email: String, password: String): UserProfile {
        val response = authApi.login(email = email, password = password)
        return UserProfile(
            userName = response.user.userName,
            email = response.user.email,
        )
    }
}

