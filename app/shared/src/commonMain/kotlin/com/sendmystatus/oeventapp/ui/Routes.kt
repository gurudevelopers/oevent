package com.sendmystatus.oeventapp.ui

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Welcome : Route
    @Serializable
    data object Demo : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object EventTemplate : Route

    @Serializable
    data class EventCreate(val selectedTemplateName: String) : Route

    @Serializable
    data object Merchant : Route

    @Serializable
    data object BusinessDetail : Route

    @Serializable
    data object BusinessEventSetup : Route

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
