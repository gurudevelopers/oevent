package com.sendmystatus.oeventapp.data.model.event

import kotlinx.serialization.Serializable


@Serializable
data class EventDTO(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String? = null,
    val isPublic: Boolean = true,
    val startDateAndTime: Long,
    val endDateAndTime: Long,
    val location: String,
    val venueName: String,
)

@Serializable
data class EventInviteDTO(
    val id: String,
    val eventId: String,
    val contact: List<EventInviteContact>,//mobile or app or email
    val message: String,
    val status: String,
    val createdDate: Long,
    val modifiedDate: Long,
)

@Serializable
data class EventInviteContactDTO(
    val id: String,
    val name: String,
    val contact: String,//mobile or app or email
    val message: String,
)

@Serializable
data class EventInviteContactAcknowledgeDTO(
    val id: String,
    val contactId: String,//EventInviteContact id
    val message: String,
    val acknowledge: String, //yes or no or may be or pending
    val createdDate: Long,
    val modifiedDate: Long,
)


@Serializable
data class EventTemplateDTO(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String,
)

@Serializable
data class EventFeedbackDTO(
    val id: String,
    val rating: Int,
    val comment: String,
    val userId: String,
    val eventId: String,
    val timestamp: Long,
)

@Serializable
data class EventSettingDTO(
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
    val createDate: Long,
    val modifyDate: Long,
)

@Serializable
data class EventAttendanceDTO(
    val id: String,
    val userId: String,
    val eventId: String,
    val status: String,
    val checkedInDate: Long,
    val checkedOutDate: Long,
)

@Serializable
data class EventProgramDTO(
    val id: String,
    val eventId: String,
    val title: String,
    val description: String,
    val startDateAndTime: Long,
    val endDateAndTime: Long,
    val status: String,
    val createdDate: Long,
    val modifiedDate: Long,
)