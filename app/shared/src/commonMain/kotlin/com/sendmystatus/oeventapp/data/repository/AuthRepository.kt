package com.sendmystatus.oeventapp.data.repository

import com.sendmystatus.oeventapp.data.model.*
import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.sendmystatus.oeventapp.di.AppScope
import me.tatarka.inject.annotations.Inject

@Inject
@AppScope
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
