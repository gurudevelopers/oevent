package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sendmystatus.oeventapp.ui.event.PriceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import me.tatarka.inject.annotations.Inject

data class EventSettingUiState(
    val isFree: Boolean = true,
    val isInPerson: Boolean = false,
    val instructionToJoin: String = "",
    val currency: String = "USD",
    val tokenPrefix: String = "OPEN_",
    val capacity: Int = 100,
    val sliderPosition: Float = 0f,
    val status: String = "active",
    val priceItems: List<PriceItem> = listOf(PriceItem())
)

class EventSettingViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(EventSettingUiState())
    val uiState = _uiState.asStateFlow()

    fun updateIsFree(isFree: Boolean) {
        _uiState.update { it.copy(isFree = isFree) }
    }

    fun updateIsInPerson(isInPerson: Boolean) {
        _uiState.update { it.copy(isInPerson = isInPerson) }
    }

    fun updateInstructionToJoin(instruction: String) {
        _uiState.update { it.copy(instructionToJoin = instruction) }
    }

    fun updateCurrency(currency: String) {
        _uiState.update { it.copy(currency = currency) }
    }

    fun updateTokenPrefix(prefix: String) {
        _uiState.update { it.copy(tokenPrefix = prefix) }
    }

    fun updateCapacity(capacity: Int) {
        _uiState.update { it.copy(capacity = capacity) }
    }

    fun updateSliderPosition(position: Float) {
        _uiState.update { 
            it.copy(
                sliderPosition = position,
                capacity = (position * 100_000).toInt()
            ) 
        }
    }

    fun updateStatus(status: String) {
        _uiState.update { it.copy(status = status) }
    }

    fun addPriceItem() {
        _uiState.update { it.copy(priceItems = it.priceItems + PriceItem()) }
    }

    fun removePriceItem(index: Int) {
        _uiState.update { 
            it.copy(priceItems = it.priceItems.toMutableList().apply { removeAt(index) }) 
        }
    }

    fun updatePriceItemLabel(index: Int, label: String) {
        _uiState.update { state ->
            val newList = state.priceItems.toMutableList()
            newList[index] = newList[index].copy(label = label)
            state.copy(priceItems = newList)
        }
    }

    fun updatePriceItemValue(index: Int, value: String) {
        _uiState.update { state ->
            val newList = state.priceItems.toMutableList()
            newList[index] = newList[index].copy(value = value)
            state.copy(priceItems = newList)
        }
    }

    fun saveSettings(eventId: String) {
        // Handle save
    }
}
