package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Attendee are registered for an event
 */
@Serializable
data class AttendeeRegistrationToEventDTO(
    val id: String,
    val numberOfGuest: Int,
    val price: Double,
    val guests: Map<String, Int> = mapOf("adult" to  1),
    val status: String,
    val eventId: String,
    val enrolledDataAndTime: Long,
    val cancelledData: Long,
    val userId: String,
)

