package com.sendmystatus.oeventapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.compose.ui.tooling.preview.Preview
import com.sendmystatus.oeventapp.data.model.event.Event
import com.sendmystatus.oeventapp.di.appModules
import com.sendmystatus.oeventapp.ui.DemoQuickAccess
import com.sendmystatus.oeventapp.ui.RewardScreen
import com.sendmystatus.oeventapp.ui.Route
import com.sendmystatus.oeventapp.ui.ScannerScreen
import com.sendmystatus.oeventapp.ui.event.CreateEventScreen
import com.sendmystatus.oeventapp.ui.event.CreateSettingEventScreen
import com.sendmystatus.oeventapp.ui.event.EventTemplateScreen
import com.sendmystatus.oeventapp.ui.event.program.AddProgramScreen
import com.sendmystatus.oeventapp.ui.event.program.EventProgramScreen
import com.sendmystatus.oeventapp.ui.onboard.CreateBusinessDetailScreen
import com.sendmystatus.oeventapp.ui.onboard.CreateBusinessRegScreen
import com.sendmystatus.oeventapp.ui.onboard.CreateMerchantAccountScreen
import com.sendmystatus.oeventapp.ui.onboard.LoginScreen
import com.sendmystatus.oeventapp.ui.onboard.OtpVerificationScreen
import com.sendmystatus.oeventapp.ui.onboard.WelcomeScreen
import com.sendmystatus.oeventapp.ui.theme.OEventTheme
import com.sendmystatus.oeventapp.ui.user.ProfileScreen
import com.sendmystatus.oeventapp.ui.viewmodel.AuthState
import com.sendmystatus.oeventapp.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.uuid.Uuid

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModules())
    }) {
        AppContent()
    }
}

@Composable
fun AppContent() {
    val logoutFlow = koinInject<SharedFlow<Unit>>()
    val authViewModel = koinViewModel<AuthViewModel>()
    val authState by authViewModel.state.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        logoutFlow.collect {
            navController.navigate(Route.Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.OtpSent -> {
                val mobile = (authState as AuthState.OtpSent).mobileNumber
                navController.navigate(Route.Otp(mobileNumber = mobile))
                authViewModel.resetState()
            }

            is AuthState.Success -> {
                navController.navigate(Route.Scanner)
                authViewModel.resetState()
            }

            else -> {}
        }
    }

    OEventTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.Demo
            ) {
                composable<Route.Demo> {
                    DemoQuickAccess(
                        onNavigate = { route ->
                            try {
                                navController.navigate(route) {
                                    launchSingleTop = true
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    )
                }

                composable<Route.EventProgram> {
                    EventProgramScreen(
                        onBack = { navController.popBackStack() },
                        onEventAdded = {
                            navController.navigate(Route.EventProgramAdd) {
                                launchSingleTop = true
                            }
                        },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.EventProgramAdd> {
                    AddProgramScreen(
                        onBack = { navController.popBackStack() },
                        onEventAdded = {
                            navController.navigate(Route.EventProgramAdd) {
                                popUpTo<Route.EventProgram> {
                                    inclusive = false
                                }
                            }
                        },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.Welcome> {
                    WelcomeScreen(
                        onAttendeeClick = { navController.navigate(Route.Login) },
                        onMerchantClick = { navController.navigate(Route.Merchant) }
                    )
                }

                composable<Route.Login> {
                    LoginScreen(
                        onSendOtpClick = { mobile ->
                            authViewModel.sendOtp(mobile)
                        },
                        onBack = { navController.popBackStack() },
                        isLoading = authState is AuthState.Loading,
                        errorMessage = (authState as? AuthState.Error)?.message
                    )
                }

                composable<Route.Merchant> {
                    CreateMerchantAccountScreen(
                        onMerchantSubmitClick = {
                            navController.navigate(Route.BusinessDetail)
                        },
                        onBack = { navController.popBackStack() },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.BusinessDetail> {
                    CreateBusinessDetailScreen(
                        onBusinessSubmitClick = {
                            navController.navigate(Route.BusinessEventSetup)
                        },
                        onBack = { navController.popBackStack() },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.EventTemplate> {
                    EventTemplateScreen(
                        onSelected = { name ->
                            if (name.isEmpty()) {
                                navController.popBackStack()
                                return@EventTemplateScreen
                            }
                            navController.navigate(Route.EventCreate(name)) {
                                launchSingleTop = true
                            }
                        },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.Profile> {
                    ProfileScreen(
                        onSave = { navController.popBackStack() },
                        onBack = { navController.popBackStack() },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.EventCreate> { backEntyr ->
                    val eventTemp: Route.EventCreate = backEntyr.toRoute()

                    CreateEventScreen(
                        eventTemp.selectedTemplateName,
                        onBack = {
                            navController.popBackStack()
                        },
                        viewModel = koinViewModel(),
                        onEventAdded = {
                            println("backstack: size: ${navController.currentBackStack.value.size} value ${navController.currentBackStack.value}")
                            navController.navigate(Route.EventSetting(it.id, it.name)) {
                                popUpTo(Route.EventTemplate) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Route.EventSetting> { backEntyr ->
                    val eventTemp: Route.EventSetting = backEntyr.toRoute()

                    CreateSettingEventScreen(
                        eventTemp.eventId,
                        eventTemp.eventName,
                        onBack = {
                            navController.popBackStack()
                        },
                        viewModel = koinViewModel()
                    )
                }

                composable<Route.BusinessEventSetup> {
                    CreateBusinessRegScreen(
                        onBusinessSubmitClick = {
                            navController.navigate(Route.BusinessEventSetup)
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable<Route.Otp> { backStackEntry ->
                    val otpRoute: Route.Otp = backStackEntry.toRoute()
                    OtpVerificationScreen(
                        mobileNumber = otpRoute.mobileNumber,
                        onVerifyClick = { otp ->
                            authViewModel.verifyOtp(otpRoute.mobileNumber, otp)
                        },
                        onBack = { navController.popBackStack() },
                        isLoading = authState is AuthState.Loading,
                        errorMessage = (authState as? AuthState.Error)?.message
                    )
                }

                composable<Route.Scanner> {
                    ScannerScreen(
                        onScan = { data ->
                            println("data: $data")
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
