package com.kmp.feature.auth.data.remote

import com.kmp.feature.auth.data.remote.dto.LoginRequestDto
import com.kmp.feature.auth.data.remote.dto.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorAuthApi(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : AuthApi {

    override suspend fun login(email: String, password: String): LoginResponseDto {
        return httpClient.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(email = email, password = password))
        }.body()
    }
}

