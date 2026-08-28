package com.sendmystatus.oeventapp.ui.event

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import com.sendmystatus.oeventapp.ui.SingleChoiceSegmentedButton
import com.sendmystatus.oeventapp.ui.StatusDropdown
import com.sendmystatus.oeventapp.ui.viewmodel.EventSettingViewModel
import io.ktor.client.request.invoke
import kotlin.random.Random

@Composable
fun CreateSettingEventScreen(
    eventId: String,
    eventName: String,
    onBack: () -> Unit,
    viewModel: EventSettingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text("${eventName.uppercase()}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.saveSettings(eventId)
                    println("Event saved: $eventId")
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(56.dp)


            ) {
                Text("Save")
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Event Type",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            SingleChoiceSegmentedButton(
                Modifier.fillMaxWidth(),
                onSelected = { label, index ->
                    viewModel.updateIsInPerson(label == "In-Person")
                }
            )


            OutlinedTextField(
                value = uiState.instructionToJoin,
                onValueChange = { viewModel.updateInstructionToJoin(it) },
                label = { Text("Instruction") },
                modifier = Modifier.fillMaxWidth()
            )


            Text(
                text = "Event Registration Type",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
            )
            SingleChoiceSegmentedButton(
                Modifier.fillMaxWidth(),
                onSelected = { label, index ->
                    viewModel.updateIsFree(label == "Free")
                },
                options = listOf("Free", "Paid")
            )

            if (uiState.isFree.not()) {
                Text(
                    text = "Price by Category",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            }
            AnimatedVisibility(visible = uiState.isFree.not()) {

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(

                        modifier = Modifier.padding(12.dp),

                        // 2. Adds 12.dp of space vertically between each TextField
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {


                        DynamicPriceForm(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            viewModel = viewModel
                        )
                        Text(
                            text = "Choose Currency",
                            modifier = Modifier.padding(bottom = 8.dp)
                                .fillMaxWidth(),
                        )

                        OutlinedTextField(
                            value = uiState.currency,
                            onValueChange = { viewModel.updateCurrency(it) },
                            label = { Text("Currency") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        )
                    }
                }
            }

            Text(
                text = "Choose Token Prefix",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = uiState.tokenPrefix,
                onValueChange = { viewModel.updateTokenPrefix(it) },
                label = { Text("Token Prefix") },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            Text(
                text = "Choose Capacity",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(

                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    // 2. Adds 12.dp of space vertically between each TextField
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Slider(value = uiState.sliderPosition, onValueChange = {
                        viewModel.updateSliderPosition(it)
                    })
                    Text(
                        text = "(Slide to set capacity or enter value)",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    OutlinedTextField(
                        value = uiState.capacity.toString(),
                        onValueChange = {
                            val newValue = it.toIntOrNull() ?: 0
                            viewModel.updateCapacity(newValue)

                        },
                        label = { Text("Capacity") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }
            }

            Text(
                text = "Choose Status",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            StatusDropdown(
                onSelected = {
                    viewModel.updateStatus(it)
                }
            )


        }
    }
}

@Composable
@Preview
fun CreateSettingEventScreenPreview() {
    CreateSettingEventScreen(eventId = "1", eventName = "Event Name", onBack = {})
}

@Composable
@Preview
fun CreatePricePerPersonScreenPreview() {
    CreatePricePerPersonScreen()
}


@Composable
fun CreatePricePerPersonScreen() {
    var categoryName by rememberSaveable { mutableStateOf("") }
    var categoryPrice by rememberSaveable { mutableStateOf(0.0) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text("Category Name") },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = categoryPrice.toString(),
            onValueChange = { categoryPrice = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Category Price") },
            modifier = Modifier.weight(1f)
        )
    }


}

// 1. Define the data structure for a single row
data class PriceItem(
    val id: String = Random.nextLong().toString(),
    val label: String = "",
    val value: String = ""
)

@Composable
fun DynamicPriceForm(
    modifier: Modifier,
    viewModel: EventSettingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.priceItems.forEachIndexed { index, item ->
            key(item.id) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = item.label,
                        onValueChange = { newlabel ->
                            viewModel.updatePriceItemLabel(index, newlabel)
                        },
                        label = { Text("Category") },
                        modifier = Modifier.weight(2f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = item.value,
                        onValueChange = { newValue ->
                            viewModel.updatePriceItemValue(index, newValue)
                        },
                        label = { Text("price") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )

                    if (uiState.priceItems.size > 1) {
                        IconButton(onClick = { viewModel.removePriceItem(index) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove item",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
//        }

        // 3. Button to add a new item
        Button(
            onClick = {
                viewModel.addPriceItem()
            },
            modifier = Modifier
//                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Add another")
        }
    }
}
