package com.sendmystatus.oeventapp.data.local.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun fromStringDoubleMap(value: Map<String, Double>?): String? = value?.let { json.encodeToString(it) }

    @TypeConverter
    fun toStringDoubleMap(value: String?): Map<String, Double>? = value?.let { json.decodeFromString(it) }

    @TypeConverter
    fun fromStringIntMap(value: Map<String, Int>?): String? = value?.let { json.encodeToString(it) }

    @TypeConverter
    fun toStringIntMap(value: String?): Map<String, Int>? = value?.let { json.decodeFromString(it) }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { json.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String>? = value?.let { json.decodeFromString(it) }
}
