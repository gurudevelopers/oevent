package com.sendmystatus.oeventapp.data

import com.sendmystatus.oeventapp.data.model.*
import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.storage.createTokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(
    private val client: HttpClient,
    val tokenStorage: TokenStorage = createTokenStorage()
) {
    private val baseUrl = "https://oevent.com"

    suspend fun login(mobileNumber: String): AuthResponse {
        return client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(mobileNumber))
        }.body()
    }

    suspend fun register(name: String, mobile: String, email: String): AuthResponse {
        return client.post("$baseUrl/registration") {
            contentType(ContentType.Application.Json)
            setBody(RegistrationRequest(name, mobile, email))
        }.body()
    }

    suspend fun verifyOtp(mobile: String, otp: String): AuthResponse {
        val response = client.post("$baseUrl/otp") {
            contentType(ContentType.Application.Json)
            setBody(OtpRequest(mobile, otp))
        }.body<AuthResponse>()

        if (response.token.isNotBlank()) {
            tokenStorage.saveTokens(
                AuthTokens(
                    accessToken = response.token,
                    refreshToken = response.refreshToken ?: ""
                )
            )
        }
        return response
    }

    suspend fun getDashboard(token: String? = null): DashboardResponse {
        return client.get("$baseUrl/dashboard") {
            if (token != null) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
    }

    suspend fun logout() {
        tokenStorage.clearTokens()
    }
}

object ApiClient {
    val tokenStorage = createTokenStorage()
    val factory = HttpClientFactory(tokenStorage)
    val client = factory.create()
    val service = ApiService(client, tokenStorage)
}
