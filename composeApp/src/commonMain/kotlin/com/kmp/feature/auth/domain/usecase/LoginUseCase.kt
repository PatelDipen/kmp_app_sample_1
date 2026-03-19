package com.kmp.feature.auth.domain.usecase

import com.kmp.feature.auth.domain.model.UserProfile
import com.kmp.feature.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): UserProfile {
        return authRepository.login(email = email, password = password)
    }
}

