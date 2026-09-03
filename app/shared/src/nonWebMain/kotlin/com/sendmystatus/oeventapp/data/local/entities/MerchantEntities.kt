package com.sendmystatus.oeventapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "merchants")
data class MerchantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val logo: String,
    val contactPersonName: String,
    val contactPersonEmail: String,
    val contactPersonPhone: String,
    val address: String,
    val website: String,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long
)

@Entity(tableName = "merchant_event_catalogs")
data class MerchantEventCatalogEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val merchantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long
)

@Entity(tableName = "merchant_event_staff")
data class MerchantEventStaffEntity(
    @PrimaryKey val id: String,
    val merchantId: String,
    val eventId: String,
    val name: String,
    val phone: String,
    val role: String,
    val status: String,
    val createdTimestamp: Long,
    val modifiedTimestamp: Long
)

@Entity(tableName = "merchant_registrations")
data class MerchantRegistrationToEventEntity(
    @PrimaryKey val id: String,
    val merchantId: String,
    val eventId: String,
    val price: Double,
    val timestamp: Long,
    val status: String,
    val paymentType: String
)

@Entity(tableName = "customer_transactions")
data class CustomerTransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val merchantId: String,
    val eventId: String,
    val tokenRedeemed: Int,
    val tokenRedeemedTimestamp: Long
)
