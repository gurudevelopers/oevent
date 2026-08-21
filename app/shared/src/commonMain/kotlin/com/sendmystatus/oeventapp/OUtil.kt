package com.sendmystatus.oeventapp

object OUtil {

    fun isValidPhoneNumber(phone: String): Boolean {
        val phoneRegex = Regex("^\\+?[0-9]{10,15}$")
        return phone.matches(phoneRegex)
    }
}

enum class EventsType{
    CONFERENCE,
    NETWORK,
    FESTIVAL,
    WORKSHOP

}