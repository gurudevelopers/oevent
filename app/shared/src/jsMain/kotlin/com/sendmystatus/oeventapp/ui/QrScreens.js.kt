package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun ScannerScreen(
    onScan: (String) -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(Res.string.scanner_not_supported))
    }
}

@Composable
actual fun RewardScreen(
    rewardData: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(Res.string.scanner_not_supported))
    }
}
