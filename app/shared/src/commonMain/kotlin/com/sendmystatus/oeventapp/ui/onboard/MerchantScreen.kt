package com.sendmystatus.oeventapp.ui.onboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_create_account
import oeventapp.app.shared.generated.resources.btn_verify
import oeventapp.app.shared.generated.resources.create_account_title
import oeventapp.app.shared.generated.resources.label_business_name
import oeventapp.app.shared.generated.resources.label_contact_name
import oeventapp.app.shared.generated.resources.label_email
import oeventapp.app.shared.generated.resources.label_mobile_number
import oeventapp.app.shared.generated.resources.resend_code
import oeventapp.app.shared.generated.resources.verify_subtitle
import oeventapp.app.shared.generated.resources.verify_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMerchantAccountScreen(
    onMerchantSubmitClick: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
//    errorMessage: String? = null
) {
    val scrollState = rememberScrollState()
    var businessName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var contactPersonName by remember { mutableStateOf("") }

    val canSubmit by remember { derivedStateOf { businessName.isNotBlank() && email.isNotBlank() && mobileNumber.isNotBlank() && contactPersonName.isNotBlank() }  }


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
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.create_account_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = businessName,
                onValueChange = { businessName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_business_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contactPersonName,
                onValueChange = { contactPersonName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_contact_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_mobile_number)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),

                )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_email)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                )



            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {onMerchantSubmitClick() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = canSubmit && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(Res.string.btn_create_account))
                }
            }

           /* if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }*/
        }
    }
}