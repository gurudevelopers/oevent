package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sendmystatus.oeventapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class OtpSent(val mobileNumber: String) : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state = _state.asStateFlow()

    fun sendOtp(mobileNumber: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                //val response = authRepository.login(mobileNumber)
                _state.value = AuthState.OtpSent(mobileNumber)
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Failed to send OTP")
            }
        }
    }

    fun verifyOtp(mobileNumber: String, otp: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                //val response = authRepository.verifyOtp(mobileNumber, otp)
                _state.value = AuthState.Success("dummy_token")
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Invalid OTP")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _state.value = AuthState.Idle
        }
    }

    fun resetState() {
        _state.value = AuthState.Idle
    }
}
