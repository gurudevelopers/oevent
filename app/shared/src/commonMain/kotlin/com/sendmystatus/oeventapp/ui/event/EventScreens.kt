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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.data.model.event.Event
import com.sendmystatus.oeventapp.ui.DateAndTimeRow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.btn_continue
import oeventapp.app.shared.generated.resources.clear
import oeventapp.app.shared.generated.resources.error_event_date_start_greater_than_end
import oeventapp.app.shared.generated.resources.event_template_title
import oeventapp.app.shared.generated.resources.label_end
import oeventapp.app.shared.generated.resources.label_event_description
import oeventapp.app.shared.generated.resources.label_event_location
import oeventapp.app.shared.generated.resources.label_event_name
import oeventapp.app.shared.generated.resources.label_event_start_date
import oeventapp.app.shared.generated.resources.label_event_venue_name
import oeventapp.app.shared.generated.resources.label_start
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.uuid.Uuid

@Composable
fun CreateEventScreen(
    templateName: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    eventObj: Event? = null,
    onEventAdded: (Event) -> Unit
) {
    val currentTime = rememberSaveable {
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    val defaultEndTime = rememberSaveable {
        (Clock.System.now().plus(1.hours)).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    var eventStartTime by rememberSaveable { mutableStateOf(eventObj?.startDateAndTime ?: currentTime) }
    var eventEndTime by rememberSaveable { mutableStateOf(eventObj?.endDateAndTime ?: defaultEndTime) }
    var eventLocation by rememberSaveable { mutableStateOf(eventObj?.location ?: "") }
    var eventVenue by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf(eventObj?.name ?: "") }
    var description by rememberSaveable { mutableStateOf(eventObj?.description ?: "") }
    var isEventPublic by rememberSaveable { mutableStateOf(eventObj?.isPublic ?: true) }

    val isDateError by remember {
        derivedStateOf {
            val start = eventStartTime
            val end = eventEndTime
            (end < start).also {
                println(" date issue")
            }

        }
    }

    val isButtonEnabled by remember {
        derivedStateOf {
            isDateError.not()
                    && (name.isNotBlank().and(name.length > 5))

        }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        bottomBar = {
            Button(
                onClick = {
                    val startTs = eventStartTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                    val endTs = eventEndTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()

                    val event = if (eventObj != null) {
                        // UPDATE CASE:
                        // .copy() reuses the existing ID, Type, and Icon automatically.
                        // It ONLY changes the fields you list below.
                        eventObj.copy(
                            name = name,
                            description = description,
                            location = eventLocation,
                            venueName = eventVenue,
                            startTimestamp = startTs,
                            endTimestamp = endTs,
                            isPublic = isEventPublic
                        )
                    } else {
                        // CREATE CASE:
                        // We create a brand new Event with a new UUID.
                        Event(
                            id = Uuid.random().toString(),
                            type = templateName,
                            name = name,
                            description = description,
                            location = eventLocation,
                            venueName = eventVenue,
                            startTimestamp = startTs,
                            endTimestamp = endTs,
                            isPublic = isEventPublic,
                            icon = "default_icon" // Provide your default icon
                        )
                    }



                    // Now do something with 'event' (e.g., save to DB or pass to callback)
                    println("Event saved: $event")
                    onEventAdded(event)

                },
                modifier = Modifier.fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = isButtonEnabled

            ) {

                Text(stringResource(Res.string.btn_continue))

            }

        },
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Create Event for $templateName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
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
                        contentDescription = stringResource(Res.string.clear),
                        modifier = Modifier.clickable { name = "" }
                            .visible(name.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(Res.string.label_event_description)) },
                minLines = 2,
                maxLines = 4,
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
                        contentDescription = stringResource(Res.string.clear),
                        modifier = Modifier.clickable { description = "" }
                            .visible(description.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = eventLocation,
                onValueChange = {
                    eventLocation = it
                },
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
                        contentDescription = stringResource(Res.string.clear),
                        modifier = Modifier.clickable { eventLocation = "" }
                            .visible(eventLocation.isNotBlank())

                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = eventVenue,
                onValueChange = {
                    eventVenue = it
                },
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
                        contentDescription = stringResource(Res.string.clear),
                        modifier = Modifier.clickable { eventVenue = "" }
                            .visible(eventVenue.isNotBlank())


                    )
                }
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
                DateAndTimeRow(
                    label = stringResource(Res.string.label_start),
                    onDateTimeClick = {
                        eventStartTime = it
                    },
                    timeStamp = eventStartTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                )
                DateAndTimeRow(
                    label = stringResource(Res.string.label_end),
                    onDateTimeClick = {
                        eventEndTime = it
                    },
                    timeStamp = eventEndTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()

                )
                Text(
                    text = if (isDateError) stringResource(Res.string.error_event_date_start_greater_than_end) else "",
                    color = MaterialTheme.colorScheme.error,
                )

            }


            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Is this event public?",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge

                )

                Switch(checked = isEventPublic, onCheckedChange = { isEventPublic = it })

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
        templateName = "Conference",
        onBack = {},
        eventObj = null,
        onEventAdded = {}
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

    var selectedTemplate by rememberSaveable { mutableStateOf("") }
    val isButtonEnabled by remember { derivedStateOf { selectedTemplate.isNotBlank() } }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            Button(
                onClick = {
                    println("selectedTemplate: $selectedTemplate")
                    onSelected(selectedTemplate)
                },
                modifier = Modifier.fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                enabled = isButtonEnabled


            ) {

                Text(stringResource(Res.string.btn_continue))

            }

        },
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.event_template_title),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onSelected("") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
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

