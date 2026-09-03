package com.sendmystatus.oeventapp.data.local.datasource

import com.sendmystatus.oeventapp.data.local.dao.*
import com.sendmystatus.oeventapp.data.local.mapper.*
import com.sendmystatus.oeventapp.data.model.event.*
import com.sendmystatus.oeventapp.data.model.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AttendeeLocalDataSourceImpl(
    private val attendeeDao: AttendeeDao
) : AttendeeLocalDataSource {
    override fun getAttendeesByEvent(eventId: String): Flow<List<AttendeeRegistrationToEvent>> {
        return attendeeDao.getAttendeesByEvent(eventId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveAttendee(attendee: AttendeeRegistrationToEvent) {
        attendeeDao.saveAttendee(attendee.toEntity())
    }

    override suspend fun deleteAttendee(id: String) {
        attendeeDao.deleteAttendee(id)
    }
}

class EventLocalDataSourceImpl(
    private val eventDao: EventDao
) : EventLocalDataSource {
    override fun getEvents(): Flow<List<Event>> {
        return eventDao.getEvents().map { entities -> entities.map { it.toDomain() } }
    }

    override fun getEventById(id: String): Flow<Event?> {
        return eventDao.getEventById(id).map { it?.toDomain() }
    }

    override suspend fun saveEvent(event: Event) {
        eventDao.saveEvent(event.toEntity())
    }

    override fun getEventTemplates(): Flow<List<EventTemplate>> {
        return eventDao.getEventTemplates().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveEventTemplate(template: EventTemplate) {
        eventDao.saveEventTemplate(template.toEntity())
    }

    override fun getEventSettings(eventId: String): Flow<EventSetting?> {
        return eventDao.getEventSettings(eventId).map { it?.toDomain() }
    }

    override suspend fun saveEventSetting(setting: EventSetting) {
        eventDao.saveEventSetting(setting.toEntity())
    }
}

class MerchantLocalDataSourceImpl(
    private val merchantDao: MerchantDao
) : MerchantLocalDataSource {
    override fun getMerchants(): Flow<List<Merchant>> {
        return merchantDao.getMerchants().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveMerchant(merchant: Merchant) {
        merchantDao.saveMerchant(merchant.toEntity())
    }

    override fun getMerchantCatalog(merchantId: String): Flow<List<MerchantEventCatalog>> {
        return merchantDao.getMerchantCatalog(merchantId).map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveMerchantCatalog(catalog: MerchantEventCatalog) {
        merchantDao.saveMerchantCatalog(catalog.toEntity())
    }
}

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser().map { it?.toDomain() }
    }

    override suspend fun saveUser(user: User) {
        userDao.saveUser(user.toEntity())
    }

    override suspend fun deleteUser() {
        userDao.deleteUser()
    }
}
