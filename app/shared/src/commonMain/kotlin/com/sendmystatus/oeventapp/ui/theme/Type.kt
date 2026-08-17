package com.sendmystatus.oeventapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Roboto is standard for Google
val RobotoFontFamily = FontFamily.SansSerif

@Immutable
data class CustomTypography(
    val small: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 12.sp
    ),
    val medium: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 16.sp
    ),
    val large: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 20.sp
    ),
    val extraLarge: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 28.sp
    )
)

val LocalCustomTypography = staticCompositionLocalOf { CustomTypography() }

// Standard Material 3 Typography mapping
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)

// Helper functions for bold, italic, underline as requested
fun TextStyle.bold() = this.copy(fontWeight = FontWeight.Bold)
fun TextStyle.italic() = this.copy(fontStyle = FontStyle.Italic)
fun TextStyle.underline() = this.copy(textDecoration = TextDecoration.Underline)
