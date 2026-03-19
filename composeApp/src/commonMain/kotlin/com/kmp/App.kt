package com.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.kmp.core.network.ApiConfig
import com.kmp.feature.auth.data.remote.KtorAuthApi
import com.kmp.feature.auth.data.repository.AuthRepositoryImpl
import com.kmp.feature.auth.domain.model.UserProfile
import com.kmp.feature.auth.domain.usecase.LoginUseCase
import com.kmp.feature.auth.presentation.LoginScreen
import com.kmp.feature.auth.presentation.LoginViewModel
import com.kmp.feature.home.presentation.HomeScreen
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private sealed interface AppRoute {
    data object Login : AppRoute
    data class Home(val user: UserProfile) : AppRoute
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var route by remember { mutableStateOf<AppRoute>(AppRoute.Login) }

        val client = remember {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            encodeDefaults = true
                        }
                    )
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                    level = LogLevel.INFO
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose { client.close() }
        }

        val scope = rememberCoroutineScope()
        val loginUseCase = remember(client) {
            val api = KtorAuthApi(httpClient = client, baseUrl = ApiConfig.baseUrl)
            LoginUseCase(AuthRepositoryImpl(api))
        }

        val loginViewModel = remember(loginUseCase, scope) {
            LoginViewModel(
                loginUseCase = loginUseCase,
                scope = scope,
                onLoginSuccess = { user -> route = AppRoute.Home(user) },
            )
        }
        val uiState by loginViewModel.uiState.collectAsState()

        when (val current = route) {
            AppRoute.Login -> {
                LoginScreen(
                    uiState = uiState,
                    onEmailChanged = loginViewModel::onEmailChanged,
                    onPasswordChanged = loginViewModel::onPasswordChanged,
                    onLoginClick = loginViewModel::login,
                )
            }

            is AppRoute.Home -> HomeScreen(user = current.user)
        }
    }
}