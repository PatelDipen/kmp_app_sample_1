package com.kmp.feature.auth.domain.repository

import com.kmp.feature.auth.domain.model.UserProfile

interface AuthRepository {
    suspend fun login(email: String, password: String): UserProfile
}

