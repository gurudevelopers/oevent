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
import androidx.compose.runtime.collectAsState
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
import org.koin.compose.viewmodel.koinViewModel
import com.sendmystatus.oeventapp.data.model.event.EventProgram
import com.sendmystatus.oeventapp.ui.DateAndTimeRow
import com.sendmystatus.oeventapp.ui.SingleChoiceSegmentedButton
import com.sendmystatus.oeventapp.ui.StatusDropdown
import com.sendmystatus.oeventapp.ui.viewmodel.AddProgramViewModel
import com.sendmystatus.oeventapp.ui.viewmodel.EventProgramViewModel
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.event_program_name_title
import oeventapp.app.shared.generated.resources.label_event_description
import oeventapp.app.shared.generated.resources.label_event_program_name_start_time
import org.jetbrains.compose.resources.stringResource


@Composable
fun EventProgramScreen(
    onBack: () -> Unit,
    onEventAdded: (List<EventProgram>) -> Unit,
    viewModel: EventProgramViewModel = koinViewModel()
) {

    val listOfPrograms by viewModel.listOfPrograms.collectAsState()
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
    onBack: () -> Unit,
    onEventAdded: (EventProgram) -> Unit,
    eventProgram: EventProgram? = null,// is user want to view the program or edit it
    modifier: Modifier = Modifier,
    viewModel: AddProgramViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

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
            value = uiState.programName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.updateName(it) },
            label = { Text(stringResource(Res.string.event_program_name_title)) }
        )
        OutlinedTextField(
            value = uiState.programDescriptor,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.updateDescriptor(it) },
            label = { Text(stringResource(Res.string.label_event_description)) }
        )

        OutlinedTextField(
            value = uiState.location,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.updateLocation(it) },
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
                viewModel.updateStatus(it)
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
                        viewModel.updateIsRequired(index == 1)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    options = listOf("Not Required", "Required")
                )

                AnimatedVisibility(uiState.isRequired) {
                    SingleChoiceSegmentedButton(
                        onSelected = { label, index ->
                            viewModel.updateIsPaid(index == 1)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        options = listOf("Free", "Paid")
                    )


                }
                AnimatedVisibility(uiState.isPaid) {
                    OutlinedTextField(
                        value = uiState.price,
                        onValueChange = { viewModel.updatePrice(it) },
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
