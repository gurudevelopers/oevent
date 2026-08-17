package com.sendmystatus.oeventapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val mobileNumber: String
)

@Serializable
data class RegistrationRequest(
    val name: String,
    val mobileNumber: String,
    val email: String
)

@Serializable
data class OtpRequest(
    val mobileNumber: String,
    val otp: String
)

@Serializable
data class AuthResponse(
    val token: String,
    val message: String = "",
    val refreshToken: String? = null
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

@Serializable
data class DashboardResponse(
    val userName: String,
    val eventCount: Int,
    val rewards: List<String>
)
