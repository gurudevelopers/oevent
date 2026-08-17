package com.sendmystatus.oeventapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sendmystatus.oeventapp.ui.LoginScreen
import com.sendmystatus.oeventapp.ui.OtpVerificationScreen
import com.sendmystatus.oeventapp.ui.RewardScreen
import com.sendmystatus.oeventapp.ui.Route
import com.sendmystatus.oeventapp.ui.ScannerScreen
import com.sendmystatus.oeventapp.ui.WelcomeScreen

import com.sendmystatus.oeventapp.ui.theme.OEventTheme

@Composable
@Preview
fun App() {
    OEventTheme {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.Welcome
            ) {
                composable<Route.Welcome> {
                    WelcomeScreen(
                        onAttendeeClick = { navController.navigate(Route.Login) },
                        onMerchantClick = { /* Handle Merchant flow */ }
                    )
                }
                composable<Route.Login> {
                    LoginScreen(
                        onSendOtpClick = { mobile ->
                            navController.navigate(Route.Otp(mobileNumber = mobile))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Route.Otp> { backStackEntry ->
                    val otpRoute: Route.Otp = backStackEntry.toRoute()
                    OtpVerificationScreen(
                        mobileNumber = otpRoute.mobileNumber,
                        onVerifyClick = { navController.navigate(Route.Scanner) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Route.Scanner> {
                    ScannerScreen(
                        onScan = { data ->
                            navController.navigate(Route.Reward(data = data))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Route.Reward> { backStackEntry ->
                    val rewardRoute: Route.Reward = backStackEntry.toRoute()
                    RewardScreen(
                        rewardData = rewardRoute.data,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}