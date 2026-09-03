package com.sendmystatus.oeventapp.data.local.database

import androidx.room.*
import com.sendmystatus.oeventapp.data.local.dao.*
import com.sendmystatus.oeventapp.data.local.entities.*
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        AttendeeEntity::class,
        EventEntity::class,
        EventInviteEntity::class,
        EventInviteContactEntity::class,
        EventTemplateEntity::class,
        EventSettingEntity::class,
        EventFeedbackEntity::class,
        EventAttendanceEntity::class,
        EventProgramEntity::class,
        MerchantEntity::class,
        MerchantEventCatalogEntity::class,
        MerchantEventStaffEntity::class,
        MerchantRegistrationToEventEntity::class,
        CustomerTransactionEntity::class,
        UserEntity::class,
        UserRegistrationEntity::class,
        UserPreferenceForEventEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
@ConstructedBy(OEventDatabaseConstructor::class)
abstract class OEventDatabase : RoomDatabase() {
    abstract fun attendeeDao(): AttendeeDao
    abstract fun eventDao(): EventDao
    abstract fun merchantDao(): MerchantDao
    abstract fun userDao(): UserDao
}

expect object OEventDatabaseConstructor : RoomDatabaseConstructor<OEventDatabase> {
    override fun initialize(): OEventDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<OEventDatabase>
): OEventDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
