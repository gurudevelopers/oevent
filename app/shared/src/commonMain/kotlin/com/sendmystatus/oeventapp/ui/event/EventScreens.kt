package com.sendmystatus.oeventapp.ui.event

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_continue
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.mutableMapOf

@Composable
fun CreateEventScreen(
    templateName: String,

    ) {
    /**
     * @Serializable
     * data class Event(
     *     val id: String,
     *     val name: String,
     *     val description: String,
     *     val type: String,
     *     val icon: String? = null,
     *     val isPublic: Boolean = true,
     *     val startDate: LocalDate,
     *     val endDate: LocalDate,
     *     val startTime: LocalTime,
     *     val endTime: LocalTime,
     *     val location: String,
     *     val venueName: String,
     * )
     */

    Scaffold(
        bottomBar = {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,


                ) {

                Text(stringResource(Res.string.btn_continue))

            }

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Event",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
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

        }
    }


}

@Composable
fun CreateEventSetupScreen(

) {


}

@Composable
@Preview
fun CreateEventSetupScreenPreview() {
    CreateEventSetupScreen()
}

@Composable
@Preview
fun CreateEventScreenPreview() {
    CreateEventScreen(
        templateName = TODO()
    )
}

@Composable
@Preview
fun EventTemplateScreenPreview() {
    EventTemplateScreen(
        onSelected = TODO()
    )
}

@Composable
fun EventTemplateScreen(onSelected: (String) -> Unit) {
    /**
     * @Serializable
     * data class EventTemplate(
     *     val id: String,
     *     val name: String,
     *     val description: String,
     *     val type: String,
     *     val icon: String,
     * )
     */
    val list = mapOf(
        "Conference" to Icons.Filled.MeetingRoom,
        "Workshop" to Icons.Filled.Workspaces,
        "Networking" to Icons.Filled.Groups,
        "Festival" to Icons.Filled.Celebration,
        "Performance" to Icons.Filled.Theaters,
        "Other" to Icons.Filled.Event
    )

    var selectedTemplate by remember { mutableStateOf("") }
    val isButtonEnabled by remember { derivedStateOf { selectedTemplate.isNotBlank() } }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    println("selectedTemplate: $selectedTemplate")
                    onSelected(selectedTemplate)
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = isButtonEnabled


                ) {

                Text(stringResource(Res.string.btn_continue))

            }

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Event",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                text = "Select Event Template",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {

                list.forEach { template ->
                    item {
                        val isSelected = selectedTemplate == template.key
                        val isCardEnabled = selectedTemplate.isEmpty() || isSelected

                        ElevatedCard(
                            modifier = Modifier.then(
                                if (isSelected) Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CardDefaults.elevatedShape
                                ) else Modifier
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            enabled = isCardEnabled,
                            onClick = {
                                selectedTemplate = if (isSelected) "" else template.key
                            }
                        ) {
                           
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp), // Padding inside the card
                                horizontalAlignment = Alignment.CenterHorizontally, // Centers items horizontally
                                verticalArrangement = Arrangement.Center // Centers items vertically if height is constrained
                            ) {
                                Icon(
                                    imageVector = template.value,
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = template.key,

                                    )
                            }
                        }
                    }
                }


            }
        }


    }
}

