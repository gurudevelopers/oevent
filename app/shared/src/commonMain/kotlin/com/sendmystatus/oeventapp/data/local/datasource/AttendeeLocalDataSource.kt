package com.sendmystatus.oeventapp.data.local.datasource

import com.sendmystatus.oeventapp.data.model.event.AttendeeRegistrationToEvent
import kotlinx.coroutines.flow.Flow

interface AttendeeLocalDataSource {
    fun getAttendeesByEvent(eventId: String): Flow<List<AttendeeRegistrationToEvent>>
    suspend fun saveAttendee(attendee: AttendeeRegistrationToEvent)
    suspend fun deleteAttendee(id: String)
}
