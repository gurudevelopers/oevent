package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Composable
fun DateSelector(
    onSelectDate: (String) -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier,
    label: String = "Date",
    placeHolderName: String = "Select Date",

    ) {

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = "",
        onValueChange = { },
        modifier = modifier.fillMaxWidth(),
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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
                onDismiss()
            },
            confirmButton = {
                TextButton(onClick = {
                    // onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
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
    onSelectTime: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Time",
    placeHolderName: String = "Select Time",
) {
    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = "",
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

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
                onDismiss()
            },
            confirmButton = {
                TextButton(onClick = {
                    onSelectTime(timePickerState.hour, timePickerState.minute)
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