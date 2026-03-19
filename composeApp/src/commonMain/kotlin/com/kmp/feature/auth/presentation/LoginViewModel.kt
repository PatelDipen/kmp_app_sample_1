package com.kmp.feature.auth.presentation

import com.kmp.feature.auth.domain.model.UserProfile
import com.kmp.feature.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val scope: CoroutineScope,
    private val onLoginSuccess: (UserProfile) -> Unit,
) {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null) }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun login() {
        val current = _uiState.value
        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email and password are required") }
            return
        }

        scope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                loginUseCase(email = current.email.trim(), password = current.password)
            }.onSuccess { profile ->
                _uiState.update { it.copy(isLoading = false) }
                onLoginSuccess(profile)
            }.onFailure { throwable ->
                val message = throwable.message ?: "Login failed"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
            }
        }
    }
}

