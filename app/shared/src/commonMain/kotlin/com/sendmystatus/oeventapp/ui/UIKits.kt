package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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