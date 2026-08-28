package com.sendmystatus.oeventapp.data.repository.auth

import com.sendmystatus.oeventapp.data.model.AuthResponse
import com.sendmystatus.oeventapp.data.model.DashboardResponse
import com.sendmystatus.oeventapp.data.model.LoginRequest
import com.sendmystatus.oeventapp.data.model.OtpRequest
import com.sendmystatus.oeventapp.data.model.RegistrationRequest
import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

@Single
class AuthRepository(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage
) {
    suspend fun login(mobileNumber: String): AuthResponse {
        return client.post("login") {
            setBody(LoginRequest(mobileNumber))
        }.body()
    }

    suspend fun register(name: String, mobile: String, email: String): AuthResponse {
        return client.post("registration") {
            setBody(RegistrationRequest(name, mobile, email))
        }.body()
    }

    suspend fun verifyOtp(mobile: String, otp: String): AuthResponse {
        val response = client.post("otp") {
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

    suspend fun getDashboard(): DashboardResponse {
        return client.get("dashboard").body()
    }

    suspend fun logout() {
        tokenStorage.clearTokens()
    }

    suspend fun getTokens(): AuthTokens? {
        return tokenStorage.getTokens()
    }
}