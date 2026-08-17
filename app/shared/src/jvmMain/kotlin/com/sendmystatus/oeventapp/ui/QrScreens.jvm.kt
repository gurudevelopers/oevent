package com.sendmystatus.oeventapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import qrgenerator.qrkitpainter.rememberQrKitPainter
import qrscanner.CameraLens
import qrscanner.QrScanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ScannerScreen(
    onScan: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.scan_qr_code)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            QrScanner(
                modifier = Modifier.fillMaxSize(),
                flashlightOn = false,
                cameraLens = CameraLens.Back,
                openImagePicker = false,
                onCompletion = { result ->
                    onScan(result)
                },
                imagePickerHandler = { /* Not used */ },
                onFailure = { /* Handle error */ }
            )
            
            Text(
                text = stringResource(Res.string.align_qr_code),
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun RewardScreen(
    rewardData: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.your_reward)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.show_qr_to_redeem),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            val qrPainter = rememberQrKitPainter(data = rewardData)
            
            Image(
                painter = qrPainter,
                contentDescription = stringResource(Res.string.reward_qr_code),
                modifier = Modifier.size(250.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = stringResource(Res.string.reward_id, rewardData),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
