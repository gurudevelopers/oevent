package com.sendmystatus.oeventapp

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


