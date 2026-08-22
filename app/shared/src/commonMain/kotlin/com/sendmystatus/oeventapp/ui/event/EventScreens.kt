package com.sendmystatus.oeventapp.ui.event

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_continue
import oeventapp.app.shared.generated.resources.label_event_date
import oeventapp.app.shared.generated.resources.label_event_description
import oeventapp.app.shared.generated.resources.label_event_end_date
import oeventapp.app.shared.generated.resources.label_event_location
import oeventapp.app.shared.generated.resources.label_event_name
import oeventapp.app.shared.generated.resources.label_event_start_date
import oeventapp.app.shared.generated.resources.label_event_time
import oeventapp.app.shared.generated.resources.label_event_venue_name
import oeventapp.app.shared.generated.resources.label_select_event
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

    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    var eventVenue by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }

    val isButtonEnabled by remember { derivedStateOf { } }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        bottomBar = {
            Button(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .height(56.dp)
                ,
                shape = MaterialTheme.shapes.medium,
//                enabled = isButtonEnabled


            ) {

                Text(stringResource(Res.string.btn_continue))

            }

        },
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text =  "Create Event for $templateName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                        scrollBehavior = scrollBehavior
            )
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



            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_event_name)) },
                singleLine = true,
                supportingText = {
                    /*if (isPhoneError) {
                        Text(
                            text = "Please enter a valid phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }*/
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.EventNote,
                        contentDescription = "Mobile"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

//                        modifier = Modifier.clickable { mobileNumber = "" }
//                            .visible(mobileNumber.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_event_description)) },
                singleLine = true,
                supportingText = {
                    /*if (isPhoneError) {
                        Text(
                            text = "Please enter a valid phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }*/
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Description,
                        contentDescription = "Mobile"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,

//                        modifier = Modifier.clickable { mobileNumber = "" }
//                            .visible(mobileNumber.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_event_location)) },
                singleLine = true,
                supportingText = {
                    /*if (isPhoneError) {
                        Text(
                            text = "Please enter a valid phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }*/
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.LocationCity,
                        contentDescription = "Mobile"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                            .visible(true)

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_event_venue_name)) },
                singleLine = true,
                supportingText = {
                    /*if (isPhoneError) {
                        Text(
                            text = "Please enter a valid phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }*/
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.EventSeat,
                        contentDescription = "Mobile"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Error",
                        //  tint = if (isPhoneError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                            .visible(true)

                    )
                }
            )


            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Is this event public?",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge

                )

                Switch(checked = isPublic, onCheckedChange = { isPublic = it })

            }


            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.label_event_start_date),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(Res.string.label_event_date)) },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.EditCalendar,
                                contentDescription = "Error",
                            )

                        }
                    )
                    Spacer(modifier = Modifier.weight(.1f))
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(Res.string.label_event_time)) },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.EditCalendar,
                                contentDescription = "Error",
                            )

                        }
                    )


                }
            }


            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.label_event_end_date),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(Res.string.label_event_date)) },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.EditCalendar,
                                contentDescription = "Error",
                            )

                        }
                    )
                    Spacer(modifier = Modifier.weight(.1f))
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(Res.string.label_event_time)) },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.EditCalendar,
                                contentDescription = "Error",
                            )

                        }
                    )


                }
            }


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
        templateName = "Conference"
    )
}

@Composable
@Preview
fun EventTemplateScreenPreview() {
    EventTemplateScreen(
        onSelected = { }
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
                },
                navigationIcon = {
                    IconButton(onClick = { onSelected("") }) {
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
                                ) else Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primaryFixed,
                                    shape = CardDefaults.elevatedShape
                                )
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

