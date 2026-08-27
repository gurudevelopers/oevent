package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BusinessDetailUiState(
    val businessType: String = "",
    val address: String = "",
    val zipCode: String = "",
    val city: String = "",
    val state: String = ""
)

data class MerchantAccountUiState(
    val contactName: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val businessName: String = ""
)

class OnboardingViewModel : ViewModel() {
    private val _businessDetailState = MutableStateFlow(BusinessDetailUiState())
    val businessDetailState = _businessDetailState.asStateFlow()

    private val _merchantAccountState = MutableStateFlow(MerchantAccountUiState())
    val merchantAccountState = _merchantAccountState.asStateFlow()

    fun updateBusinessType(type: String) {
        _businessDetailState.update { it.copy(businessType = type) }
    }

    fun updateAddress(address: String) {
        _businessDetailState.update { it.copy(address = address) }
    }

    fun updateZipCode(zip: String) {
        _businessDetailState.update { it.copy(zipCode = zip) }
    }

    fun updateCity(city: String) {
        _businessDetailState.update { it.copy(city = city) }
    }

    fun updateState(state: String) {
        _businessDetailState.update { it.copy(state = state) }
    }

    fun updateContactName(name: String) {
        _merchantAccountState.update { it.copy(contactName = name) }
    }

    fun updateMerchantMobileNumber(number: String) {
        _merchantAccountState.update { it.copy(mobileNumber = number) }
    }

    fun updateMerchantEmail(email: String) {
        _merchantAccountState.update { it.copy(email = email) }
    }

    fun updateBusinessName(name: String) {
        _merchantAccountState.update { it.copy(businessName = name) }
    }
}
