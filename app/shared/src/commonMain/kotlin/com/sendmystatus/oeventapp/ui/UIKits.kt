package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.OUtil.GoogleCalendarDateFormat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.label_end
import oeventapp.app.shared.generated.resources.label_event_date
import oeventapp.app.shared.generated.resources.label_event_time
import oeventapp.app.shared.generated.resources.label_start
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun InfoBanner(
    info: String,
    modifier: Modifier = Modifier,
    bgColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
    /* bgColor: Color = Color(0xFFF4F9F5) ,
     borderColor: Color = Color(0xFFD3E4D8)*/
) {
    Box(
        modifier = modifier
            // Allows it to stretch across the screen (adjust as needed)
            .background(
                color = bgColor, // Light greenish-gray background
                shape = RoundedCornerShape(8.dp) // Rounds the corners
            )
            .border(
                width = 1.dp,
                color = borderColor, // Slightly darker green border
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp), // Space between the border and the text
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = info,
            color = textColor, // Darker green/gray text color
            textAlign = TextAlign.Center, // Centers the text lines
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    onSelectDate: (LocalDate) -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier,
    label: String = "Date",
    placeHolderName: String = "Select Date",
    withInput: Boolean = false,
    currentTime: LocalDateTime = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    )
    var showDatePicker by remember { mutableStateOf(false) }
    val selectedDate by remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let { millis ->
                Instant.fromEpochMilliseconds(millis)
                    .toLocalDateTime(TimeZone.UTC)
                    .date
                    .format(GoogleCalendarDateFormat)
            } ?: label

        }
    }

    if (withInput) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(placeHolderName) },
            label = { Text(label) },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = "Calender",
                    modifier = Modifier.clickable {
                        showDatePicker = true
                    }
                )
            }
        )
    } else {
        Text(
            text = selectedDate,
            modifier = Modifier.clickable { showDatePicker = true }.padding(8.dp)
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
                onDismiss()
            },
            confirmButton = {
                TextButton(onClick = {
                    println("datePickerState.selectedDateMillis: ${datePickerState.selectedDateMillis}")
                    // onDateSelected(datePickerState.selectedDateMillis)
                    datePickerState.selectedDateMillis?.let {
                        val localDate = Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.UTC)
                            .date
                        onSelectDate(localDate)
                    }

                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDismiss()
                }) {
                    Text("Cancel")
                }
            },

            ) {
            DatePicker(state = datePickerState)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelector(
    onSelectTime: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Time",
    placeHolderName: String = "Select Time",
    withInput: Boolean = false,
    currentTime: LocalDateTime = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
) {

    // Create a state for the time picker
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute,
        is24Hour = false
    )
    var showTimePicker by remember { mutableStateOf(false) }
    val selectedTime by remember {
        derivedStateOf {
            val isPm = timePickerState.hour >= 12
            val displayHour = if (timePickerState.hour % 12 == 0) 12 else timePickerState.hour % 12
            "$displayHour:${
                timePickerState.minute.toString().padStart(2, '0')
            } ${if (isPm) "PM" else "AM"}"

        }
    }

    if (withInput) {// we need to validate the input
        OutlinedTextField(
            value = selectedTime,
            onValueChange = { },
            modifier = modifier.fillMaxWidth(),
            label = { Text(label) },
            readOnly = true,
            placeholder = { Text(placeHolderName) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "Time Picker",
                    modifier = Modifier.clickable {
                        showTimePicker = true
                    }
                )
            },
            singleLine = true
        )
    } else {
        Text(
            text = selectedTime,
            modifier = Modifier.clickable { showTimePicker = true }.padding(8.dp)
        )
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
                onDismiss()
            },
            confirmButton = {
                TextButton(onClick = {
                    val tempTime = LocalTime(timePickerState.hour, timePickerState.minute)
                    onSelectTime(tempTime)
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                    onDismiss()
                }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}

@Composable
fun DateAndTimeRow(
    label: String,
    dateText: String = stringResource(Res.string.label_event_date),
    timeText: String = stringResource(Res.string.label_event_time),
    timeStamp: Long = Clock.System.now().toEpochMilliseconds(),
    onDateTimeClick: (LocalDateTime) -> Unit,
) {
    val currentTime = remember {
        Instant.fromEpochMilliseconds(timeStamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())

    }

    var dateSelected by remember {
        mutableStateOf(
            LocalDate(
                currentTime.year,
                currentTime.month,
                currentTime.day
            )
        )
    }
    var timeSelected by remember { mutableStateOf(LocalTime(currentTime.hour, currentTime.minute)) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))

        DateSelector(
            label = dateText,
            onSelectDate = { date ->
                dateSelected = date
                onDateTimeClick(LocalDateTime(dateSelected, timeSelected))

                //   onDateTimeClick(LocalDateTime(date, LocalTime.MIDNIGHT))
            },
            onDismiss = { },
            modifier = Modifier.weight(1f),
            currentTime = currentTime

        )

        TimeSelector(
            label = timeText,
            onSelectTime = {
                timeSelected = it
                onDateTimeClick(LocalDateTime(dateSelected, timeSelected))

            },
            onDismiss = { },
            modifier = Modifier.weight(1f),
            currentTime = currentTime

        )

    }
}

@Composable
fun DateTimeRow(
    label: String,
    dateText: String,
    timeText: String,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))

        Text(
            text = dateText,
            modifier = Modifier.clickable { onDateClick() }.padding(8.dp)
        )
        Text(
            text = timeText,
            modifier = Modifier.clickable { onTimeClick() }.padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCreatorScreen() {
    // 1. State for Start/End Values (Using Strings for simplicity here)
    var startDate by remember { mutableStateOf("Aug 24, 2026") }
    var startTime by remember { mutableStateOf("6:00 PM") }
    var endDate by remember { mutableStateOf("Aug 24, 2026") }
    var endTime by remember { mutableStateOf("7:00 PM") }

    // 2. Dialog Visibility State
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showStartDatePicker by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Start Row
        DateTimeRow(
            label = "Start",
            dateText = startDate,
            timeText = startTime,
            onDateClick = { showStartDatePicker = true },
            onTimeClick = { showStartTimePicker = true }
        )

        // End Row
        DateTimeRow(
            label = "End",
            dateText = endDate,
            timeText = endTime,
            onDateClick = { /* Handle End Date */ },
            onTimeClick = { /* Handle End Time */ }
        )

        // Show Start Time Picker Dialog
        if (showStartTimePicker) {
            CustomTimePickerDialog(
                onCancel = { showStartTimePicker = false },
                onConfirm = { hour, minute ->
                    // Format the hour/minute as discussed in the previous answer
                    val isPm = hour >= 12
                    val displayHour = if (hour % 12 == 0) 12 else hour % 12
                    startTime = "$displayHour:${
                        minute.toString().padStart(2, '0')
                    } ${if (isPm) "PM" else "AM"}"
                    showStartTimePicker = false
                }
            )
        }

        // Show Start Date Picker Dialog (Using built-in M3 component)
        if (showStartDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        // Convert datePickerState.selectedDateMillis to your date string
                        showStartDatePicker = false
                    }) { Text("OK") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onCancel: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = { onConfirm(timePickerState.hour, timePickerState.minute) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) { Text("Cancel") }
        },
        text = { TimePicker(state = timePickerState) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterStatusDropdown() {
    val options = remember {
        mutableStateListOf("Active", "Live", "Canceled", "Suspended")
    }
    var expanded by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    // 1. Get the focus manager to control the keyboard and cursor
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                expanded = true
            },
            label = { Text("Status") },
            placeholder = { Text("Select or type...") },
            // 2. Add a leading icon for visual context
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = null)
            },
            // 3. Dynamic Trailing Icon: Show 'X' if text exists, otherwise show arrow
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = {
                        inputText = ""
                        focusManager.clearFocus() // Hide keyboard when cleared
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear text")
                    }
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            // Round the corners a bit more for a modern look
            shape = RoundedCornerShape(12.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            singleLine = true
        )

        val filteredOptions = options.filter {
            it.contains(inputText, ignoreCase = true)
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus() // Drop focus if they tap outside
            }
        ) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        inputText = option
                        expanded = false
                        // 4. Clear focus and hide keyboard immediately upon selection
                        focusManager.clearFocus()
                    }
                )
            }

            if (inputText.isNotBlank() && !options.any {
                    it.equals(
                        inputText,
                        ignoreCase = true
                    )
                }) {
                DropdownMenuItem(
                    text = { Text("Add \"$inputText\"", fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                    onClick = {
                        options.add(inputText)
                        expanded = false
                        focusManager.clearFocus() // Hide keyboard
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropdown(
    onSelected: (String) -> Unit, modifier: Modifier = Modifier,
    options: List<String> = listOf("Active", "Live", "Canceled", "Suspended")
) {

    // 2. Track whether the dropdown is open (expanded) or closed
    var expanded by remember { mutableStateOf(false) }

    // 3. Track the currently selected value (default to the first item)
    var selectedStatus by remember { mutableStateOf(options[0]) }

    // 4. The main wrapper for the dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }, // Toggle open/close on click
        modifier = modifier
    ) {
        // 5. The visible text field that the user clicks
        OutlinedTextField(
            value = selectedStatus,
            onValueChange = {}, // Read-only, so this is empty
            readOnly = true,    // Prevents the keyboard from popping up
            label = { Text("Status") },
            trailingIcon = {
                // Adds the little down arrow icon that flips when opened
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            // The menuAnchor() modifier is CRITICAL. It tells the menu where to attach.
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth()
        )

        // 6. The actual popup menu containing your options
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close if user clicks outside
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedStatus = option // Update the selected value
                        expanded = false        // Close the menu
                        onSelected(option)
                    }
                )
            }
        }
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    onSelected: (String, Int) -> Unit = { _, _ -> },
    options: List<String> = listOf("Online", "In-Person", "Hybrid")
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onSelected(label, index)
                },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}