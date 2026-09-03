package com.sendmystatus.oeventapp.data.local.dao

import androidx.room.*
import com.sendmystatus.oeventapp.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM attendees WHERE eventId = :eventId")
    fun getAttendeesByEvent(eventId: String): Flow<List<AttendeeEntity>>

    @Upsert
    suspend fun saveAttendee(attendee: AttendeeEntity)

    @Query("DELETE FROM attendees WHERE id = :id")
    suspend fun deleteAttendee(id: String)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): Flow<EventEntity?>

    @Upsert
    suspend fun saveEvent(event: EventEntity)

    @Query("SELECT * FROM event_templates")
    fun getEventTemplates(): Flow<List<EventTemplateEntity>>

    @Upsert
    suspend fun saveEventTemplate(template: EventTemplateEntity)

    @Query("SELECT * FROM event_settings WHERE eventId = :eventId")
    fun getEventSettings(eventId: String): Flow<EventSettingEntity?>

    @Upsert
    suspend fun saveEventSetting(setting: EventSettingEntity)
}

@Dao
interface MerchantDao {
    @Query("SELECT * FROM merchants")
    fun getMerchants(): Flow<List<MerchantEntity>>

    @Upsert
    suspend fun saveMerchant(merchant: MerchantEntity)

    @Query("SELECT * FROM merchant_event_catalogs WHERE merchantId = :merchantId")
    fun getMerchantCatalog(merchantId: String): Flow<List<MerchantEventCatalogEntity>>

    @Upsert
    suspend fun saveMerchantCatalog(catalog: MerchantEventCatalogEntity)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Upsert
    suspend fun saveUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteUser()
}
