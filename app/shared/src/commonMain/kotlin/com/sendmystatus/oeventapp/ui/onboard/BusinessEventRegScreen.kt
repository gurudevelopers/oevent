package com.sendmystatus.oeventapp.ui.onboard

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.input.key.Key.Companion.M
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.ui.InfoBanner
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_business_submit
import oeventapp.app.shared.generated.resources.btn_continue
import oeventapp.app.shared.generated.resources.business_detail_title
import oeventapp.app.shared.generated.resources.business_event_setup_title
import oeventapp.app.shared.generated.resources.label_booth_name
import oeventapp.app.shared.generated.resources.label_business_address
import oeventapp.app.shared.generated.resources.label_business_city
import oeventapp.app.shared.generated.resources.label_business_state
import oeventapp.app.shared.generated.resources.label_business_type
import oeventapp.app.shared.generated.resources.label_business_zip
import oeventapp.app.shared.generated.resources.label_select_event
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun BusinessEventSetupScreenPreview() {
    CreateBusinessRegScreen(
        onBusinessSubmitClick = {},
        onBack = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBusinessRegScreen(
    onBusinessSubmitClick: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean = false,
//    errorMessage: String? = null
) {
    val scrollState = rememberScrollState()
    var eventName by remember { mutableStateOf("") }
    var boothName by remember { mutableStateOf("") }

    val canSubmit by remember { derivedStateOf { eventName.isNotBlank() && boothName.isNotBlank() } }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(Res.string.business_event_setup_title),
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
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = canSubmit && !isLoading
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InfoBanner("Enrolled to the event")
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_select_event)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = boothName,
                onValueChange = { boothName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_booth_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                )


            /* if (errorMessage != null) {
                 Spacer(modifier = Modifier.height(16.dp))
                 Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
             }*/
        }
    }
}