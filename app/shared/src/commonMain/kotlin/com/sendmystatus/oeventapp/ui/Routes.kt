package com.sendmystatus.oeventapp.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Welcome : Route

    @Serializable
    data object Login : Route

    @Serializable
    data class Otp(val mobileNumber: String) : Route

    @Serializable
    data class Dashboard(val userType: UserType) : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object Scanner : Route

    @Serializable
    data class Reward(val data: String) : Route
}

enum class UserType {
    ADMIN,
    SIGNED_IN_USER,
    MERCHANT,
    GUST

}
