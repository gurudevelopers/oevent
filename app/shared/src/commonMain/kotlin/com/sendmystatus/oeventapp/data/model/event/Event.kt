package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Event(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val icon: String? = null,
    val isPublic: Boolean = true,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val location: String,
    val venueName: String,
) {
    val startDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(startTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val endDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(endTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class EventInvite(
    val id: String,
    val eventId: String,
    val contact: List<EventInviteContact>,//mobile or app or email
    val message: String,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val modifyDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

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
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val modifyDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}


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
    val timestamp: Long,
) {
    val feedbackDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())

}

@Serializable
data class EventSetting(
    val id: String,
    val eventId: String,
    val isFree: Boolean = true,
    val price: Map<String, Double> = mapOf("default" to 0.0),
    val isOnline: Boolean = false,
    val currency: String = "USD",
    val tokenPrefix: String = "OPEN_",
    val capacity: Int = 100,
    val status: String = "active",
    val images: List<String>,
    val modifyTimestamp: Long,
    val createdTimestamp: Long,
) {
    val createDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val modifyDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifyTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class EventAttendance(
    val id: String,
    val userId: String,
    val eventId: String,
    val status: String,
    val checkedInTimestamp: Long,
    val checkedOutTimestamp: Long,
) {
    val checkedInDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(checkedInTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val checkedOutDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(checkedOutTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class EventProgram(
    val id: String,
    val eventId: String,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createdDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val modifiedDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}





