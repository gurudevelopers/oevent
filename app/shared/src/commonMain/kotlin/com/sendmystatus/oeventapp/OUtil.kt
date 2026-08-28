package com.sendmystatus.oeventapp

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

object OUtil {

    fun isValidPhoneNumber(phone: String): Boolean {
        val phoneRegex = Regex("^\\+?[0-9]{10,15}$")
        return phone.matches(phoneRegex)
    }

    val GoogleCalendarDateFormat = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED) // "Tue"
        chars(", ")                                   // ", "
        monthName(MonthNames.ENGLISH_ABBREVIATED)     // "Aug"
        char(' ')                                     // " "
        day()                                  // "25"
        chars(", ")                                   // ", "
        year()                                        // "2026"
    }
}

enum class EventsType{
    CONFERENCE,
    NETWORK,
    FESTIVAL,
    WORKSHOP

}

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // 1. Strip everything that isn't a digit
        val trimmed = text.text.filter { it.isDigit() }.take(10)

        var out = ""
        for (i in trimmed.indices) {
            if (i == 0) out += "("
            if (i == 3) out += ") "
            if (i == 6) out += "-"
            out += trimmed[i]
        }

        // 2. Define how the cursor position maps back and forth
        val phoneNumberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset <= 3) return offset + 1 // accounts for '('
                if (offset <= 6) return offset + 5 // accounts for '(', ') '
                return offset + 6                  // accounts for '(', ') ', '-'
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 1) return 0
                if (offset <= 6) return offset - 1
                if (offset <= 10) return offset - 5
                return 10.coerceAtMost(offset - 6)
            }
        }

        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }
}



