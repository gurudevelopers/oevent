package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Attendee are registered for an event
 */
@Serializable
data class AttendeeRegistrationToEvent(
    val id: String,
    val numberOfGuest: Int,
    val price: Double,
    val guests: Map<String, Int> = mapOf("adult" to 1),
    val status: String,
    val eventId: String,
    val enrolledTimestamp: Long,
    val cancelledTimestamp: Long,
    val userId: String,
) {
    val enrolledDate: LocalDateTime
        get() = Instant.fromEpochMilliseconds(enrolledTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val cancelledDate: LocalDateTime
        get() = Instant.fromEpochMilliseconds(cancelledTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

