package com.sendmystatus.oeventapp.ui.splash

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.ui.theme.AppTheme
import com.sendmystatus.oeventapp.ui.theme.bold
import com.sendmystatus.oeventapp.ui.theme.italic
import oeventapp.app.shared.generated.resources.Res
import oeventapp.app.shared.generated.resources.compose_multiplatform
import oeventapp.app.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun WelcomeScreen(
    onAttendeeClick: () -> Unit,
    onMerchantClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Event Token Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.welcome_title),
            style = AppTheme.typography.extraLarge.bold(),
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.welcome_subtitle),
            style = AppTheme.typography.medium.italic(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        ElevatedButton(
            onClick = onMerchantClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(Res.string.btn_merchant),
                style = AppTheme.typography.large.bold(),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onAttendeeClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(Res.string.btn_attendee),
                style = AppTheme.typography.large.bold(),
            )
        }



        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { /* TODO */ }) {
            Text(stringResource(Res.string.already_have_account), color = Color.Gray)
        }
    }
}

@Composable
@Preview
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onAttendeeClick = {},
        onMerchantClick = {}
    )
}



