package com.sendmystatus.oeventapp.ui.onboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Stroller
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.ui.InfoBanner
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_business_submit
import oeventapp.app.shared.generated.resources.btn_create_account
import oeventapp.app.shared.generated.resources.business_detail_title
import oeventapp.app.shared.generated.resources.create_account_title
import oeventapp.app.shared.generated.resources.label_business_address
import oeventapp.app.shared.generated.resources.label_business_city
import oeventapp.app.shared.generated.resources.label_business_name
import oeventapp.app.shared.generated.resources.label_business_state
import oeventapp.app.shared.generated.resources.label_business_type
import oeventapp.app.shared.generated.resources.label_business_zip
import oeventapp.app.shared.generated.resources.label_contact_name
import oeventapp.app.shared.generated.resources.label_email
import oeventapp.app.shared.generated.resources.label_mobile_number
import org.jetbrains.compose.resources.stringResource


@Preview
@Composable
fun BusinessDetailScreenPreview() {
    CreateBusinessDetailScreen(
        onBusinessSubmitClick = {},
        onBack = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBusinessDetailScreen(
    onBusinessSubmitClick: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
//    errorMessage: String? = null
) {
    val scrollState = rememberScrollState()
    var businessType by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    val canSubmit by remember { derivedStateOf { city.isNotBlank() && businessType.isNotBlank() && address.isNotBlank() && zipCode.isNotBlank() && state.isNotBlank() } }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var focusRequester = remember { FocusRequester() }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(Res.string.business_detail_title),
                        fontSize = 22.sp,
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
                onClick = { onBusinessSubmitClick() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp)
                    .focusRequester(focusRequester),
                shape = MaterialTheme.shapes.medium,
                enabled = canSubmit && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(Res.string.btn_business_submit))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InfoBanner("Provide Business type and details.")

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = businessType,
                onValueChange = { businessType = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_business_type)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Storefront,
                        contentDescription = "Business"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Business",
                        modifier = Modifier.clickable { businessType = "" }
                            .visible(businessType.isNotBlank())
                    )
                }

            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_business_address)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.LocationCity,
                        contentDescription = "Business"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Business",
                        modifier = Modifier.clickable { address = "" }.visible(address.isNotBlank())
                    )
                }

            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(Res.string.label_business_city)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Business",
                            modifier = Modifier.clickable { city = "" }.visible(city.isNotBlank())
                        )
                    }
                )
                OutlinedTextField(
                    value = zipCode,
                    onValueChange = { zipCode = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(Res.string.label_business_zip)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Business",
                            modifier = Modifier.clickable { zipCode = "" }
                                .visible(zipCode.isNotBlank())
                        )
                    }
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { state = it },
                label = { Text(stringResource(Res.string.label_business_state)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Business",
                        modifier = Modifier.clickable { state = "" }.visible(state.isNotBlank())
                    )
                }

            )



            Spacer(modifier = Modifier.height(48.dp))


            /* if (errorMessage != null) {
                 Spacer(modifier = Modifier.height(16.dp))
                 Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
             }*/
        }
    }
}