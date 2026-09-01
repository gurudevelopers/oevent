package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

data class InvitationUiState(
    val invitations: List<String> = emptyList(), // Placeholder for real invitation data
    val notificationCount: Int = 3
)

@KoinViewModel
class InvitationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InvitationUiState())
    val uiState = _uiState.asStateFlow()

    fun onJoinEventClick() {
        // Handle join event
    }

    fun onEnterCodeClick() {
        // Handle enter code
    }

    fun onScanQrClick() {
        // Handle scan QR
    }
}
