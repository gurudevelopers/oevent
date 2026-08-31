package com.sendmystatus.oeventapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.system.exitProcess

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OEventApp",
    ) {
        App(
            onAppClose = { exitProcess(-1) }
        )
    }
}