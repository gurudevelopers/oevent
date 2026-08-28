package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sendmystatus.oeventapp.data.model.event.EventProgram
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EventProgramViewModel : ViewModel() {
    private val _listOfPrograms = MutableStateFlow<List<EventProgram>>(emptyList())
    val listOfPrograms = _listOfPrograms.asStateFlow()

    fun addProgram(program: EventProgram) {
        _listOfPrograms.update { it + program }
    }
}

data class AddProgramUiState(
    val programName: String = "",
    val programDescriptor: String = "",
    val location: String = "",
    val status: String = "Published",
    val isRequired: Boolean = false,
    val isPaid: Boolean = false,
    val price: String = ""
)

@KoinViewModel
class AddProgramViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddProgramUiState())
    val uiState = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(programName = name) }
    }

    fun updateDescriptor(descriptor: String) {
        _uiState.update { it.copy(programDescriptor = descriptor) }
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun updateStatus(status: String) {
        _uiState.update { it.copy(status = status) }
    }

    fun updateIsRequired(isRequired: Boolean) {
        _uiState.update { it.copy(isRequired = isRequired) }
    }

    fun updateIsPaid(isPaid: Boolean) {
        _uiState.update { it.copy(isPaid = isPaid) }
    }

    fun updatePrice(price: String) {
        _uiState.update { it.copy(price = price) }
    }
}
