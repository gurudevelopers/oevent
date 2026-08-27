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
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sendmystatus.oeventapp.ui.StatusDropdown
import io.ktor.client.request.invoke
import kotlin.random.Random

@Composable
fun CreateSettingEventScreen(eventId: String, eventName: String, onBack: () -> Unit) {

    val scrollState = rememberScrollState()
    var isFree by rememberSaveable { mutableStateOf(true) }
    var isOnline by rememberSaveable { mutableStateOf(false) }
    var currency by rememberSaveable { mutableStateOf("USD") }
    var tokenPrefix by rememberSaveable { mutableStateOf("OPEN_") }
    var capacity by rememberSaveable { mutableStateOf(100) }
    var status by rememberSaveable { mutableStateOf("active") }
    var images by rememberSaveable { mutableStateOf(listOf<String>()) }


    var categoryPrice by rememberSaveable { mutableStateOf(mapOf<String, Double>()) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var sliderPosition by rememberSaveable { mutableStateOf(0f) }

    val sliderState =
        rememberSliderState(
            // Only allow multiples of 10. Excluding the endpoints of `valueRange`,
            // there are 9 steps (10, 20, ..., 90).
            steps = 1,
            valueRange = 0f..10000f,
            onValueChangeFinished = {

                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
        )

    /*  LaunchedEffect(sliderPosition) {
  //        capacity = sliderState.value.toInt()
          println("LaunchedEffect sliderPosition: $sliderPosition")
          capacity = sliderPosition.toInt() * 100_000
          println("LaunchedEffect sliderPosition: $capacity")
      }*/

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
                    // Now do something with 'event' (e.g., save to DB or pass to callback)
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Is this event online?",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                )
                Switch(checked = isOnline, onCheckedChange = { isOnline = it })
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(
                    bottom = 16.dp,
                    top = 16.dp
                ),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Is this event free?",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                )
                Switch(checked = isFree, onCheckedChange = { isFree = it })
            }
//            if (isFree.not()) {
            AnimatedVisibility(visible = isFree.not()) {
                Column(Modifier.fillMaxWidth()) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(
                            bottom = 16.dp,
                            top = 16.dp
                        ),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                    Text(
                        text = "Add price",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                            .padding(bottom = 8.dp)
                    )
                    DynamicPriceForm(modifier = Modifier.fillMaxWidth())
                    Text(
                        text = "Choose Currency",
                        modifier = Modifier.padding(bottom = 8.dp)
                            .fillMaxWidth(),
                    )

                    OutlinedTextField(
                        value = currency,
                        onValueChange = { currency = it },
                        label = { Text("Currency") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(
                    bottom = 16.dp,
                    top = 16.dp
                ),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            Text(
                text = "Choose Token Prefix",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = tokenPrefix,
                onValueChange = { tokenPrefix = it },
                label = { Text("Token Prefix") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Text(
                text = "Choose Capacity",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )


//            Slider(state = sliderState)
            Slider(value = sliderPosition, onValueChange = {
                sliderPosition = it
                capacity = (sliderPosition * 100_000).toInt()
            })
            Text(text = "(Slide to set capacity or enter value)",
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = capacity.toString(),
                onValueChange = {
                    val newValue = it.toIntOrNull() ?: 0
                    capacity = newValue

                },
                label = { Text("Capacity") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Text(
                text = "Choose Status",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp).padding(bottom = 8.dp)
            )

            StatusDropdown(
                onSelected = {
                    status = it
                }
            )


            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(
                    bottom = 16.dp,
                    top = 16.dp
                ),
                thickness = 2.dp,
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
fun DynamicPriceForm(modifier: Modifier) {
    // 2. Create an observable list of items, starting with one empty item
    val priceItems = remember { mutableStateListOf(PriceItem()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Text(
            text = "Contact Details",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 3. Display the items in a scrollable list
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {*/
        priceItems.forEachIndexed { index, item ->
            key(item.id) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = item.label,
                        onValueChange = { newlabel ->
                            println("newValue: $newlabel")
                            val tem = item.copy(label = newlabel)
                            println("tem: $tem")
                            priceItems[index] = item.copy(label = newlabel)
                        },
                        label = { Text("Category") },
                        modifier = Modifier.weight(2f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = item.value,
                        onValueChange = { newValue ->
                            println("newValue: $newValue")
                            val tem = item.copy(value = newValue)
                            println("tem: $tem")
                            priceItems[index] = item.copy(value = newValue)
//                                newValue ->
//                                println("newValue: $newValue")
//                                val tem = item.copy(value = newValue)
//                                println("tem: $tem")
//                                priceItems[index] = item.copy(value = newValue)
                        },
                        label = { Text("price") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )

                    if (priceItems.size > 1) {
                        IconButton(onClick = { priceItems.removeAt(index) }) {
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
                priceItems.add(PriceItem())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Add another")
        }
    }
}