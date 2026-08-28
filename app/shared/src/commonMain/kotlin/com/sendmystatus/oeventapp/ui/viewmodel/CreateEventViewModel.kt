package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sendmystatus.oeventapp.data.model.event.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

import me.tatarka.inject.annotations.Inject

data class CreateEventUiState(
    val name: String = "",
    val description: String = "",
    val eventLocation: String = "",
    val eventVenue: String = "",
    val eventStartTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val eventEndTime: LocalDateTime = (Clock.System.now().plus(1.hours)).toLocalDateTime(TimeZone.currentSystemDefault()),
    val isEventPublic: Boolean = true
)

class CreateEventViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(eventLocation = location) }
    }

    fun updateVenue(venue: String) {
        _uiState.update { it.copy(eventVenue = venue) }
    }

    fun updateStartTime(startTime: LocalDateTime) {
        _uiState.update { it.copy(eventStartTime = startTime) }
    }

    fun updateEndTime(endTime: LocalDateTime) {
        _uiState.update { it.copy(eventEndTime = endTime) }
    }

    fun updateVisibility(isPublic: Boolean) {
        _uiState.update { it.copy(isEventPublic = isPublic) }
    }

    fun initFromEvent(event: Event) {
        _uiState.update { 
            it.copy(
                name = event.name,
                description = event.description,
                eventLocation = event.location ?: "",
                eventVenue = event.venueName ?: "",
                eventStartTime = event.startDateAndTime,
                eventEndTime = event.endDateAndTime,
                isEventPublic = event.isPublic
            )
        }
    }
}
