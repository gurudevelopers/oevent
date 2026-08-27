package com.sendmystatus.oeventapp.ui.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AddToHomeScreen
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.AssistantDirection
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.AssistantDirection
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sendmystatus.oeventapp.ui.InfoBanner
import com.sendmystatus.oeventapp.ui.theme.AppTheme
import com.sendmystatus.oeventapp.ui.theme.bold
import com.sendmystatus.oeventapp.ui.viewmodel.OnboardingViewModel
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_continue
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
import oeventapp.app.shared.generated.resources.welcome_title
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun CreateMerchantAccountScreenPreview() {
    CreateMerchantAccountScreen(
        onMerchantSubmitClick = {},
        onBack = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMerchantAccountScreen(
    onMerchantSubmitClick: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
    viewModel: OnboardingViewModel = viewModel { OnboardingViewModel() }
//    errorMessage: String? = null
) {
    val uiState by viewModel.merchantAccountState.collectAsState()
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }

    val canSubmit by remember { 
        derivedStateOf { 
            uiState.businessName.isNotBlank() && 
            uiState.email.isNotBlank() && 
            uiState.mobileNumber.isNotBlank() && 
            uiState.contactName.isNotBlank() 
        } 
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,

                title = {
                    Text(
                        text = stringResource(Res.string.create_account_title),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {onMerchantSubmitClick() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp)
                    .focusRequester(focusRequester),
                shape = MaterialTheme.shapes.medium,
                enabled = canSubmit && !isLoading,

                ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(Res.string.btn_continue))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InfoBanner("Provide Business and contact details.")
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = uiState.businessName,
                onValueChange = { viewModel.updateBusinessName(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_business_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Business,
                        contentDescription = "Business"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

                        modifier = Modifier.clickable { viewModel.updateBusinessName("") }
                            .visible(uiState.businessName.isNotBlank())

                    )
                }

            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.contactName,
                onValueChange = { viewModel.updateContactName(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_contact_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

                        modifier = Modifier.clickable { viewModel.updateContactName("") }
                            .visible(uiState.contactName.isNotBlank())

                    )
                }

            )
            Spacer(modifier = Modifier.height(21.dp))

            OutlinedTextField(
                value = uiState.mobileNumber,
                onValueChange = { viewModel.updateMerchantMobileNumber(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_mobile_number)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Phone"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

                        modifier = Modifier.clickable { viewModel.updateMerchantMobileNumber("") }
                            .visible(uiState.mobileNumber.isNotBlank())

                    )
                }

            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.updateMerchantEmail(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_email)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                      //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

                        modifier = Modifier.clickable { viewModel.updateMerchantEmail("") }
                            .visible(uiState.email.isNotBlank())

                    )
                }

            )
            /* if (errorMessage != null) {
                 Spacer(modifier = Modifier.height(16.dp))
                 Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
             }*/
        }
    }
}
