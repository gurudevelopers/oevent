package com.sendmystatus.oeventapp.data.local.datasource

import com.sendmystatus.oeventapp.data.model.event.*
import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {
    fun getEvents(): Flow<List<Event>>
    fun getEventById(id: String): Flow<Event?>
    suspend fun saveEvent(event: Event)
    
    fun getEventTemplates(): Flow<List<EventTemplate>>
    suspend fun saveEventTemplate(template: EventTemplate)
    
    fun getEventSettings(eventId: String): Flow<EventSetting?>
    suspend fun saveEventSetting(setting: EventSetting)
}
