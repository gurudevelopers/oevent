package com.sendmystatus.oeventapp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App(
    onAppClose = {}
) }