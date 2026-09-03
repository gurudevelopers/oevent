package com.sendmystatus.oeventapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendees")
data class AttendeeEntity(
    @PrimaryKey val id: String,
    val numberOfGuest: Int,
    val price: Double,
    val guests: Map<String, Int>,
    val status: String,
    val eventId: String,
    val enrolledTimestamp: Long,
    val cancelledTimestamp: Long,
    val userId: String
)
