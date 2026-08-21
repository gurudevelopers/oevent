package com.sendmystatus.oeventapp.ui.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.AssistantDirection
import androidx.compose.material.icons.automirrored.filled.SendToMobile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MobileOff
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.OUtil.isValidPhoneNumber
import com.sendmystatus.oeventapp.ui.Route
import com.sendmystatus.oeventapp.ui.UserType
import com.sendmystatus.oeventapp.ui.theme.AppTheme
import com.sendmystatus.oeventapp.ui.theme.bold
import com.sendmystatus.oeventapp.ui.theme.italic
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.compose_multiplatform
import oeventapp.app.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun AppPreview() {
    DemoQuickAccess(
        onNavigate = {}
    )
}

@Composable
fun DemoQuickAccess(onNavigate: (Route) -> Unit) {

    val list = listOf(
        "Guest Login",
        "Merchant Login",
        "Merchant Create Account",
        "Business Details",
        "Business Event setup",
        "Merchant DashBoard",
        "Event creation",
        "Event Roles",
        "Event Dashboard"
    )
    val navigationList = listOf(
        Route.Login,
        Route.Merchant,
        Route.BusinessDetail,
        Route.BusinessEventSetup,
        Route.Dashboard(UserType.ADMIN),
        Route.Dashboard(UserType.MERCHANT),
        Route.Dashboard(UserType.GUST),
        Route.Dashboard(UserType.SIGNED_IN_USER),
        Route.Profile,
        Route.Settings,
        Route.Scanner,
        Route.Reward,
    )

    val actionRouter = list.zip(navigationList)
    println("actionRouter: ${actionRouter::class}")
    println("actionRouter: $actionRouter")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quick Access",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            actionRouter.forEach {
                item {
                    ElevatedButton(onClick = {
                        println("got to ${it.second}")

                        onNavigate(it.second as Route)

                    }) {
                        Text(text = it.first)
                    }
                }
            }

            navigationList.forEach {
                item {
                    ElevatedButton(onClick = {
                        println("got to ${it}")

                        onNavigate(it as Route)

                    }) {
                        Text(text = it.toString())
                    }
                }
            }
            /*list.forEach {
                item {
                    ElevatedButton(onClick = { *//*TODO*//* }) {
                        Text(text = it)
                    }
                }
            }*/
        }

    }


}

@Composable
fun WelcomeScreen(
    onAttendeeClick: () -> Unit,
    onMerchantClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Event Token Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.welcome_title),
            style = AppTheme.typography.extraLarge.bold(),
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.welcome_subtitle),
            style = AppTheme.typography.medium.italic(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        ElevatedButton(
            onClick = onMerchantClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(Res.string.btn_merchant),
                style = AppTheme.typography.large.bold(),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onAttendeeClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(Res.string.btn_attendee),
                style = AppTheme.typography.large.bold(),
            )
        }



        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { /* TODO */ }) {
            Text(stringResource(Res.string.already_have_account), color = Color.Gray)
        }
    }
}

@Composable
@Preview
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onAttendeeClick = {},
        onMerchantClick = {}
    )
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(
        onSendOtpClick = {},
        onBack = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSendOtpClick: (String) -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var mobileNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()

    val isPhoneValid by remember { derivedStateOf { isValidPhoneNumber(mobileNumber) && mobileNumber.isNotBlank() } }
    val isEmailValid by remember { derivedStateOf { email.matches(emailRegex) } }

    val isPhoneError by remember { derivedStateOf { !isPhoneValid && mobileNumber.isNotBlank() } }
    val isEmailError by remember { derivedStateOf { !isEmailValid && email.isNotBlank() } }

    val isButtonEnabled by remember {
        derivedStateOf {
            isPhoneValid && isEmailValid
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.login_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tab-like selection (Mobile/Email) - Placeholder
            Text(
                text = "Mobile or Email",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            /* HorizontalDivider(
                 modifier = Modifier.width(60.dp),
                 thickness = 2.dp,
                 color = MaterialTheme.colorScheme.primary
             )*/

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { input ->
                    if (input.all { it.isDigit() || it == '+' } && input.length <= 10) {
                        mobileNumber = input
                        // Clear error as user types, or validate live
                    }
                },
                label = { Text(stringResource(Res.string.label_mobile_number)) },
                placeholder = { Text(stringResource(Res.string.placeholder_mobile)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isPhoneError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                supportingText = {
                    if (isPhoneError) {
                        Text(
                            text = "Please enter a valid phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Smartphone,
                        contentDescription = "Mobile"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

                        modifier = Modifier.clickable { mobileNumber = "" }
                            .visible(mobileNumber.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { input ->
                    email = input
                    // Clear error as user types, or validate live
                },
                label = { Text(stringResource(Res.string.label_email)) },
                placeholder = { Text(stringResource(Res.string.placeholder_email)) },
                isError = isEmailError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (isEmailError) {
                        Text(
                            text = "Please enter a valid email address",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        tint = if (isEmailError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { email = "" }
                            .visible(email.isNotBlank())

                    )
                }
            )

            Button(
                onClick = { onSendOtpClick("$mobileNumber, $email") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = isButtonEnabled && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(Res.string.btn_send_otp))
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }

            Text(
                text = stringResource(Res.string.terms_privacy_note),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    mobileNumber: String,
    onVerifyClick: (String) -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var otpCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.verify_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
            println("mobileNumber: $mobileNumber")

            Text(
                text = stringResource(Res.string.verify_subtitle, mobileNumber),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // OTP Input fields placeholder
            OutlinedTextField(
                value = otpCode,
                onValueChange = { if (it.length <= 6) otpCode = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("0 0 0 0 0 0", color = Color.Gray)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.resend_code),
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { onVerifyClick(otpCode) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = otpCode.length == 6 && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(Res.string.btn_verify))
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }
        }
    }
}

