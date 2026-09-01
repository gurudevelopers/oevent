package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sendmystatus.oeventapp.data.model.event.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

data class EventsUiState(
    val upcomingEvents: List<Event> = emptyList(),
    val pastEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val selectedCategoryIndex: Int = 0,
    val categories: List<String> = listOf("All", "Organizing", "Attending", "Merchant")
)

@KoinViewModel
class EventsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedTab(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun updateSelectedCategory(index: Int) {
        _uiState.update { it.copy(selectedCategoryIndex = index) }
    }

    fun refreshEvents() {
        // Fetch events from repository in a real app
    }
}
