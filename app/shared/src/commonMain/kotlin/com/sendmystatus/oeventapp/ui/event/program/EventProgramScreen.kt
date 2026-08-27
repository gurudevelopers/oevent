package com.sendmystatus.oeventapp.ui.event.program

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.data.model.event.EventProgram
import com.sendmystatus.oeventapp.ui.DateAndTimeRow
import com.sendmystatus.oeventapp.ui.SingleChoiceSegmentedButton
import com.sendmystatus.oeventapp.ui.StatusDropdown
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
        floatingActionButtonPosition = FabPosition.End,

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
    var location by remember { mutableStateOf("") }
    var isRequired by remember { mutableStateOf(false) }
    var isPaid by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())

            .padding(24.dp),
         horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text ="Program Details",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,

            )
        OutlinedTextField(
            value = programName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { programName = it },
            label = { Text(stringResource(Res.string.event_program_name_title)) }
        )
        OutlinedTextField(
            value = programDescriptor,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { programDescriptor = it },
            label = { Text(stringResource(Res.string.label_event_description)) }
        )

        OutlinedTextField(
            value = programDescriptor,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { programDescriptor = it },
            label = { Text("Location") }
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Text(
            text = stringResource(Res.string.label_event_program_name_start_time),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,

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
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 24.dp,
                    end = 24.dp

                ),

                // 2. Adds 12.dp of space vertically between each TextField
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

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
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        StatusDropdown(
            onSelected = {
                status = it
            },
            options = listOf("Published", "Draft", "Canceled")
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Text(
            text = "Registration",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
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

                SingleChoiceSegmentedButton(
                    onSelected = { label, index ->
                        isRequired = index == 1
                    },
                    modifier = Modifier.fillMaxWidth(),
                    options = listOf("Not Required", "Required")
                )

                AnimatedVisibility(isRequired) {
                    SingleChoiceSegmentedButton(
                        onSelected = { label, index ->
                            isPaid = index == 1
                        },
                        modifier = Modifier.fillMaxWidth(),
                        options = listOf("Free", "Paid")
                    )


                }
                AnimatedVisibility(isPaid) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        label = { Text("Price") },
                        singleLine = true
                    )

                }
            }
        }
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