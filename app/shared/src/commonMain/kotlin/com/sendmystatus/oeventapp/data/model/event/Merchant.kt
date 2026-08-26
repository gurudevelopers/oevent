package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Merchant(
    val id: String,
    val name: String,
    val description: String,
    val logo: String,
    val contactPersonName: String,
    val contactPersonEmail: String,
    val contactPersonPhone: String,
    val address: String,
    val website: String,
    val status: String = "default",
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createdDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val cancelledDate: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class MerchantEventCatalog(
    val id: String,
    val eventId: String,
    val merchantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val status: String = "available",
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createdDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val cancelledDate: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class MerchantEventStaff(
    val id: String,
    val merchantId: String,
    val eventId: String,
    val name: String,
    val phone: String,
    val role: String,
    val status: String = "available",
    val createdTimestamp: Long,
    val modifiedTimestamp: Long,
) {
    val createdDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(createdTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val cancelledDate: LocalDateTime
        get() = Instant.fromEpochMilliseconds(modifiedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class MerchantRegistrationToEvent(
    val id: String,
    val merchantId: String,
    val eventId: String,
    val price: Double = 0.0,
    val timestamp: Long,
    val status: String = "pending",
    val paymentType: String, // cash on delivery, online: credit card, debitcard, ach
) {
    val registeredDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}

@Serializable
data class CustomerTransaction(
    val id: String,
    val userId: String,
    val merchantId: String,
    val eventId: String,
    val tokenRedeemed: Int,
    val tokenRedeemedTimestamp: Long
) {
    val redeemedDateAndTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(tokenRedeemedTimestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())

}
