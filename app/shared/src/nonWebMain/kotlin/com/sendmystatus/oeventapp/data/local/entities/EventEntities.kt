package com.sendmystatus.oeventapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String?,
    val isPublic: Boolean,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val location: String,
    val venueName: String
)

@Entity(tableName = "event_invites")
data class EventInviteEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val message: String,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long
)

@Entity(tableName = "event_invite_contacts")
data class EventInviteContactEntity(
    @PrimaryKey val id: String,
    val inviteId: String,
    val name: String,
    val contact: String,
    val message: String
)

@Entity(tableName = "event_templates")
data class EventTemplateEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String
)

@Entity(tableName = "event_settings")
data class EventSettingEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val isFree: Boolean,
    val price: Map<String, Double>,
    val isOnline: Boolean,
    val currency: String,
    val tokenPrefix: String,
    val capacity: Int,
    val status: String,
    val images: List<String>,
    val modifyTimestamp: Long,
    val createdTimestamp: Long
)

@Entity(tableName = "event_feedback")
data class EventFeedbackEntity(
    @PrimaryKey val id: String,
    val rating: Int,
    val comment: String,
    val userId: String,
    val eventId: String,
    val timestamp: Long
)

@Entity(tableName = "event_attendance")
data class EventAttendanceEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val eventId: String,
    val status: String,
    val checkedInTimestamp: Long,
    val checkedOutTimestamp: Long
)

@Entity(tableName = "event_programs")
data class EventProgramEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long
)
