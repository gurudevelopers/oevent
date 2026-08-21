package com.sendmystatus.oeventapp.data.model.event

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

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
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)

@Serializable
data class MerchantEventCatalog(
    val id: String,
    val eventId: String,
    val merchantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val status: String = "available",
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)

@Serializable
data class MerchantEventStaff(
    val id: String,
    val merchantId: String,
    val eventId: String,
    val name: String,
    val phone: String,
    val role: String,
    val status: String = "available",
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime,
)

@Serializable
data class MerchantRegistrationToEvent(
    val id: String,
    val merchantId: String,
    val eventId: String,
    val price: Double = 0.0,
    val timestamp: LocalDateTime,
    val status: String = "pending",
    val paymentType: String, // cash on delivery, online: credit card, debitcard, ach
)

@Serializable
data class CustomerTransaction(
    val id: String,
    val userId: String,
    val merchantId: String,
    val eventId: String,
    val tokenRedeemed: Int,
    val tokenRedeemedDate: LocalDateTime
)
