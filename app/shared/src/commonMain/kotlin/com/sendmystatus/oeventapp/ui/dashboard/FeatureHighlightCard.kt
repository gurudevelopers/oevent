package com.sendmystatus.oeventapp.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FeatureHighlight(
    val title: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

@Composable
@Preview
fun FeatureHighlightsCardPreview() {
    val sendMyStatusFeatures = listOf(
        FeatureHighlight(
            title = "Create & manage\nevents",
            icon = Icons.Outlined.Event,
            iconColor = Color(0xFF1976D2),
            backgroundColor = Color(0xFFE8F1FF)
        ),
        FeatureHighlight(
            title = "Invite & manage\nattendees",
            icon = Icons.Outlined.GroupAdd,
            iconColor = Color(0xFF2E9B4B),
            backgroundColor = Color(0xFFEAF7EE)
        ),
        FeatureHighlight(
            title = "Onboard\nmerchants",
            icon = Icons.Outlined.Storefront,
            iconColor = Color(0xFF8E44AD),
            backgroundColor = Color(0xFFF3E8FA)
        ),
        FeatureHighlight(
            title = "Track & analyze\nactivity",
            icon = Icons.Outlined.BarChart,
            iconColor = Color(0xFFE86A17),
            backgroundColor = Color(0xFFFFEFE5)
        )
    )

    FeatureHighlightsCard(
        features = sendMyStatusFeatures,
        onHighlightClick = {},
        title = "With SendMyStatus you can"
    )
}

@Composable
fun FeatureHighlightsCard(
    features: List<FeatureHighlight>,
    modifier: Modifier = Modifier,
    onHighlightClick: (FeatureHighlight) -> Unit,
    title: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBFBFB)
        ),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                features.forEach { feature ->
                    FeatureHighlightItem(
                        feature = feature,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureHighlightItem(
    feature: FeatureHighlight,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = feature.backgroundColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = feature.title,
                tint = feature.iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = feature.title,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}
