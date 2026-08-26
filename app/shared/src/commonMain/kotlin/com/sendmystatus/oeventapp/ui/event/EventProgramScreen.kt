package com.sendmystatus.oeventapp.ui.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.data.model.event.EventProgram
import com.sendmystatus.oeventapp.ui.DateAndTimeRow
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.event_program_name_title
import oeventapp.app.shared.generated.resources.label_event_description
import oeventapp.app.shared.generated.resources.label_event_program_name_start_time
import org.jetbrains.compose.resources.stringResource


@Composable
fun EventProgramScreen(onBack: () -> Unit, onEventAdded: (List<EventProgram>) -> Unit) {

    var listOfPrograms by remember { mutableStateOf(listOf<EventProgram>()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {

        },
        bottomBar = {

        },
        floatingActionButton = {

        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {

        }
    }


}

@Composable
@Preview
fun EventProgramScreenPreview() {
    EventProgramScreen(onBack = {}, onEventAdded = {})
}

@Composable
@Preview
fun AddProgramScreenPreview() {
    AddProgramScreen(onBack = {}, onEventAdded = {})
}

@Composable
fun AddProgramScreen(
    onBack: () -> Unit, onEventAdded: (EventProgram) -> Unit,
    eventProgram: EventProgram? = null,// is user want to view the program or edit it
    modifier: Modifier = Modifier
) {

    var eventProgram by remember { mutableStateOf(null) }
    var programName by remember { mutableStateOf("") }
    var programDescriptor by remember { mutableStateOf("") }
    var programStartDate by remember { mutableStateOf("") }
    var programEndDate by remember { mutableStateOf("") }
    var programStartTime by remember { mutableStateOf("") }
    var programEndTime by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())

            .padding(16.dp),
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = programName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { programName = it },
            label = { Text(stringResource(Res.string.event_program_name_title)) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = programDescriptor,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { programDescriptor = it },
            label = { Text(stringResource(Res.string.label_event_description)) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Text(
            text = stringResource(Res.string.label_event_program_name_start_time),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Start,

            )
        Spacer(modifier = Modifier.height(12.dp))
        DateAndTimeRow(
            label = "Start",
            dateText = "Aug 24, 2026",
            timeText = "6:00 PM",
            onDateTimeClick = { },
        )
        DateAndTimeRow(
            label = "End",
            dateText = "Aug 24, 2026",
            timeText = "6:00 PM",
            onDateTimeClick = { },
        )
        /* DateSelector(
             onSelectDate = { date ->
                 //eventDate = date
             },
             onDismiss = { },
             label = stringResource(Res.string.label_event_date),
             placeHolderName = "Select Date",



         )*/

    }


}