package com.kmp.feature.auth.presentation

data class LoginUiState(
    val email: String = "dipen.d.patel@accenture.com",
    val password: String = "dipen_patel",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

