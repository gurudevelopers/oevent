package com.sendmystatus.oeventapp.ui.event

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import com.sendmystatus.oeventapp.data.model.event.Event
import com.sendmystatus.oeventapp.ui.DateAndTimeRow
import com.sendmystatus.oeventapp.ui.SingleChoiceSegmentedButton
import com.sendmystatus.oeventapp.ui.viewmodel.CreateEventViewModel
import com.sendmystatus.oeventapp.ui.viewmodel.EventTemplateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    viewModel: CreateEventViewModel = koinViewModel(),
    onEventAdded: (Event) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(eventObj) {
        eventObj?.let { viewModel.initFromEvent(it) }
    }

    val coroutineScope = rememberCoroutineScope()
    val isDateError by remember {
        derivedStateOf {
            val start = uiState.eventStartTime
            val end = uiState.eventEndTime
            (end < start).also {
                println(" date issue")
            }

        }
    }

    val isButtonEnabled by remember {
        derivedStateOf {
            isDateError.not()
                    && (uiState.name.isNotBlank().and(uiState.name.length > 5))

        }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        bottomBar = {
            Button(
                onClick = {
                    val startTs = uiState.eventStartTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                    val endTs = uiState.eventEndTime.toInstant(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()

                    val event = if (eventObj != null) {
                        // UPDATE CASE:
                        // .copy() reuses the existing ID, Type, and Icon automatically.
                        // It ONLY changes the fields you list below.
                        eventObj.copy(
                            name = uiState.name,
                            description = uiState.description,
                            location = uiState.eventLocation,
                            venueName = uiState.eventVenue,
                            startTimestamp = startTs,
                            endTimestamp = endTs,
                            isPublic = uiState.isEventPublic
                        )
                    } else {
                        // CREATE CASE:
                        // We create a brand new Event with a new UUID.
                        Event(
                            id = Uuid.random().toString(),
                            type = templateName,
                            name = uiState.name,
                            description = uiState.description,
                            location = uiState.eventLocation,
                            venueName = uiState.eventVenue,
                            startTimestamp = startTs,
                            endTimestamp = endTs,
                            isPublic = uiState.isEventPublic,
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
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    // 1. Adds 12.dp of space inside the Card's outer edges
                    modifier = Modifier.padding(12.dp),

                    // 2. Adds 12.dp of space vertically between each TextField
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.updateName(it) },
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
                                modifier = Modifier.clickable { viewModel.updateName("") }
                                    .visible(uiState.name.isNotBlank())

                            )
                        }
                    )

                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = {
                            viewModel.updateDescription(it)
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
                                modifier = Modifier.clickable { viewModel.updateDescription("") }
                                    .visible(uiState.description.isNotBlank())

                            )
                        }
                    )

                    OutlinedTextField(
                        value = uiState.eventLocation,
                        onValueChange = {
                            viewModel.updateLocation(it)
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
                                modifier = Modifier.clickable { viewModel.updateLocation("") }
                                    .visible(uiState.eventLocation.isNotBlank())

                            )
                        }
                    )

                    OutlinedTextField(
                        value = uiState.eventVenue,
                        onValueChange = {
                            viewModel.updateVenue(it)
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
                                modifier = Modifier.clickable { viewModel.updateVenue("") }
                                    .visible(uiState.eventVenue.isNotBlank())


                            )
                        }
                    )
                }

        }

            Text(
                text = stringResource(Res.string.label_event_start_date),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
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
                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp,
                        end = 12.dp,),
                ) {

                    DateAndTimeRow(
                        label = stringResource(Res.string.label_start),
                        onDateTimeClick = {
                            viewModel.updateStartTime(it)
                        },
                        timeStamp = uiState.eventStartTime.toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()
                    )
                    DateAndTimeRow(
                        label = stringResource(Res.string.label_end),
                        onDateTimeClick = {
                            viewModel.updateEndTime(it)
                        },
                        timeStamp = uiState.eventEndTime.toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()

                    )
                    Text(
                        text = if (isDateError) stringResource(Res.string.error_event_date_start_greater_than_end) else "",
                        color = MaterialTheme.colorScheme.error,
                    )

                }
            }
            Text(
                text = "Event Visibility",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,

                )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
            )
            SingleChoiceSegmentedButton(
                options = listOf("Public", "Private"),
                modifier = Modifier.fillMaxWidth(),
                onSelected = { label, index ->
                    println("label: $label, index: $index")
                    viewModel.updateVisibility(index == 0)
                    coroutineScope.launch {
                        // For an instant jump: scrollState.scrollTo(scrollState.maxValue)
                        // For a smooth animation:
                        delay(300)
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                }
            )
            AnimatedVisibility(uiState.isEventPublic.not()){
                SuggestionChip(
                    onClick = { },
                    label = { Text("Guest must be invited") }
                )
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
fun EventTemplateScreen(
    onSelected: (String) -> Unit,
    viewModel: EventTemplateViewModel = koinViewModel()
) {
    val selectedTemplate by viewModel.selectedTemplate.collectAsState()
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
                            viewModel.selectTemplate(template.key)
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

