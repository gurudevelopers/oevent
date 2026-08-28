package com.sendmystatus.oeventapp.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sendmystatus.oeventapp.PhoneNumberVisualTransformation
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_send_otp
import oeventapp.app.shared.generated.resources.label_email
import oeventapp.app.shared.generated.resources.label_mobile_number
import oeventapp.app.shared.generated.resources.login_title
import oeventapp.app.shared.generated.resources.placeholder_email
import oeventapp.app.shared.generated.resources.placeholder_mobile
import oeventapp.app.shared.generated.resources.terms_privacy_note
import org.jetbrains.compose.resources.stringResource

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
    val emailRegex = remember { "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex() }

    val isPhoneValid = remember(mobileNumber) { isValidPhoneNumber(mobileNumber) && mobileNumber.isNotBlank() }
    val isEmailValid = remember(email) { email.matches(emailRegex) }

    val isPhoneError = remember(isPhoneValid, mobileNumber) { !isPhoneValid && mobileNumber.isNotBlank() }
    val isEmailError = remember(isEmailValid, email) { !isEmailValid && email.isNotBlank() }

    val isButtonEnabled = remember(isPhoneValid, isEmailValid) { isPhoneValid && isEmailValid }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("") },
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
                trailingIcon = if (mobileNumber.isNotBlank()) {
                    {
                        IconButton(onClick = { mobileNumber = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear",
                                tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else null
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
                trailingIcon = if (email.isNotBlank()) {
                    {
                        IconButton(onClick = { email = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear",
                                tint = if (isEmailError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else null
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