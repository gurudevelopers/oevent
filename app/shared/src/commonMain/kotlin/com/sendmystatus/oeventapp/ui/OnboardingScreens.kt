package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

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
            text = "Welcome to\nEvent Token",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Choose how you want\nto continue",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onAttendeeClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("I'm an Attendee")
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedButton(
            onClick = onMerchantClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("I'm a Merchant")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        TextButton(onClick = { /* TODO */ }) {
            Text("Already have an account? Login", color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSendOtpClick: (String) -> Unit,
    onBack: () -> Unit,
) {
    var mobileNumber by remember { mutableStateOf("") }
    
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
                text = "Create your account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Tab-like selection (Mobile/Email) - Placeholder
            Text(text = "Mobile          Email", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            HorizontalDivider(modifier = Modifier.width(60.dp), thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number") },
                placeholder = { Text("(555) 123-4567") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { onSendOtpClick(mobileNumber) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = mobileNumber.isNotBlank()
            ) {
                Text("Send OTP")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "By continuing, you agree to\nTerms & Conditions and Privacy Policy",
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
    onVerifyClick: (String) -> Unit,
    onBack: () -> Unit,
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
                text = "Verify your number",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Enter the 6-digit code sent to\n(555) 123-4567",
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
                text = "Resend code in 00:30",
                fontSize = 14.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Button(
                onClick = { onVerifyClick(otpCode) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = otpCode.length == 6
            ) {
                Text("Verify")
            }
        }
    }
}

