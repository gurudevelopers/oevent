package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val canContact: Boolean = true
)

@KoinViewModel
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun updateFirstName(name: String) {
        _uiState.update { it.copy(firstName = name) }
    }

    fun updateLastName(name: String) {
        _uiState.update { it.copy(lastName = name) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateMobileNumber(number: String) {
        _uiState.update { it.copy(mobileNumber = number) }
    }

    fun updateCanContact(canContact: Boolean) {
        _uiState.update { it.copy(canContact = canContact) }
    }

    fun saveProfile() {
        // Handle save logic
    }

    fun saveShortProfile() {
        // Handle save logic
    }

}
