package com.sendmystatus.oeventapp.ui

import androidx.compose.runtime.Composable

@Composable
expect fun ScannerScreen(
    onScan: (String) -> Unit,
    onBack: () -> Unit
)

@Composable
expect fun RewardScreen(
    rewardData: String,
    onBack: () -> Unit
)
