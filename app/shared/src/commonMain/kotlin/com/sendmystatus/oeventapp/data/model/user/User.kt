package com.sendmystatus.oeventapp.data.model.user

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val createdDate: LocalDateTime,
    val status: String,
)

@Serializable
data class UserRegistration(
    val id: String,
    val userId: String,
    val status: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
    val platform: String,//mobile, browser, web, desktop
    val ipAddress: String,
)

@Serializable
data class UserPreferenceForEvent(
    val id: String,
    val userId: String,
    val eventChoiceId: String,
    val status: String,// interested
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)
