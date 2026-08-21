package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String? = null,
    val isPublic: Boolean = true,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String,
    val venueName: String,
)

@Serializable
data class EventInvite(
    val id: String,
    val eventId: String,
    val contact: List<EventInviteContact>,//mobile or app or email
    val message: String,
    val status: String,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)

@Serializable
data class EventInviteContact(
    val id: String,
    val name: String,
    val contact: String,//mobile or app or email
    val message: String,
)

@Serializable
data class EventInviteContactAcknowledge(
    val id: String,
    val contactId: String,//EventInviteContact id
    val message: String,
    val acknowledge: String, //yes or no or may be or pending
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)


@Serializable
data class EventTemplate(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String,
)

@Serializable
data class EventFeedback(
    val id: String,
    val rating: Int,
    val comment: String,
    val userId: String,
    val eventId: String,
    val timestamp: String,
)

@Serializable
data class EventSetting(
    val id: String,
    val eventId: String,
    val isFree: Boolean = true,
    val price: Map<String, Double> = mapOf("default" to 0.0),
    val isOnline: Boolean,
    val currency: String,
    val tokenPrefix: String,
    val capacity: Int = 100,
    val status: String = "active",
    val images: List<String>,
    val modifyDate: LocalDate,
)

@Serializable
data class EventAttendance(
    val id: String,
    val userId: String,
    val eventId: String,
    val status: String,
    val checkedInDate: LocalDateTime,
)





