package com.sendmystatus.oeventapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val createdDate: LocalDateTime,
    val status: String
)

@Entity(tableName = "user_registrations")
data class UserRegistrationEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val status: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
    val platform: String,
    val ipAddress: String
)

@Entity(tableName = "user_event_preferences")
data class UserPreferenceForEventEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val eventChoiceId: String,
    val status: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime
)
