package com.sendmystatus.oeventapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sendmystatus.oeventapp.ui.LoginScreen
import com.sendmystatus.oeventapp.ui.OtpVerificationScreen
import com.sendmystatus.oeventapp.ui.WelcomeScreen

enum class Screen {
    Welcome, Login, Otp
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Welcome) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.Welcome -> WelcomeScreen(
                    onAttendeeClick = { currentScreen = Screen.Login },
                    onMerchantClick = { /* Handle Merchant flow */ }
                )
                Screen.Login -> LoginScreen(
                    onSendOtpClick = { currentScreen = Screen.Otp },
                    onBack = { currentScreen = Screen.Welcome }
                )
                Screen.Otp -> OtpVerificationScreen(
                    onVerifyClick = { /* Complete onboarding */ },
                    onBack = { currentScreen = Screen.Login }
                )
            }
        }
    }
}