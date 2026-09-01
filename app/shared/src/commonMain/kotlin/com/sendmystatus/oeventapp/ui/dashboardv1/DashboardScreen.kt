package com.sendmystatus.oeventapp.ui.dashboardv1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun DashBoardPreview() {
    DashBoardScreen()
}

@Composable
fun DashBoardScreen() {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(

    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))


                DashboardHeader(name = "Dibyajyoti")
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Create an Event",
                        description = "Organize your own event\nin minutes",
                        icon = Icons.Outlined.AddBox,
                        iconTint = Color(0xFF1A73E8),
                        backgroundColor = Color(0xFFE8F1FF),
                        onClick = {}
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Join an Event",
                        description = "Enter event code\nor scan QR",
                        icon = Icons.Outlined.PersonAdd,
                        iconTint = Color(0xFF188038),
                        backgroundColor = Color(0xFFEAF7EE),
                        onClick = {}
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                EmptyStateSection()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Create an Event",
                        description = "Organize your own event\nin minutes",
                        icon = Icons.Outlined.AddBox,
                        iconTint = Color(0xFF1A73E8),
                        backgroundColor = Color(0xFFE8F1FF),
                        onClick = {}
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Join an Event",
                        description = "Enter event code\nor scan QR",
                        icon = Icons.Outlined.PersonAdd,
                        iconTint = Color(0xFF188038),
                        backgroundColor = Color(0xFFEAF7EE),
                        onClick = {}
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                
                FeatureHighlightsCard(
                    features = listOf(
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
                    ),
                    onHighlightClick = {},
                    title = "With SendMyStatus you can"
                )
                
                Spacer(modifier = Modifier.height(120.dp))
            }

            DashboardBottomNavigation(
                modifier = Modifier.align(Alignment.BottomCenter)

            )
        }
    }
}

@Composable
fun DashboardHeader(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good morning,",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1C1E)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "👋",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Text(
                text = "Let's create memories together.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            BadgedBox(
                badge = {
                    Badge(
                        containerColor = Color(0xFFD93025),
                        contentColor = Color.White,
                        modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
                    ) {
                        Text("2")
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconTint: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.3f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(iconTint, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = iconTint
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFF0F0F0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStateSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Simple Illustration using Icons and Shapes
        Box(
            modifier = Modifier
                .size(280.dp, 200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Calendar background
            Card(
                modifier = Modifier.size(140.dp, 160.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFF0F0F0))
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color(0xFF4285F4)))
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        // Grid lines for calendar
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(4) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    repeat(4) {
                                        Box(modifier = Modifier.size(16.dp).background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp)))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Decorative elements (balloons)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-100).dp, y = (-20).dp)
                    .background(Color(0xFF4285F4).copy(alpha = 0.6f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = 110.dp, y = (-30).dp)
                    .background(Color(0xFF34A853).copy(alpha = 0.3f), CircleShape)
            )
            
            // Plant
            Box(
                modifier = Modifier
                    .size(30.dp, 50.dp)
                    .offset(x = 90.dp, y = 60.dp)
                    .background(Color(0xFF34A853).copy(alpha = 0.5f), RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No events yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1C1E)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Create your first event or join an event\nto get started.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun DashboardBottomNavigation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.8f),
            shadowElevation = 12.dp,
            tonalElevation = 0.dp,
            border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
            modifier = Modifier.height(68.dp).fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToolbarItem(
                    icon = Icons.Filled.Home,
                    isSelected = true,
                    onClick = {},
                    name = "Home"
                )
                ToolbarItem(
                    icon = Icons.Outlined.Event,
                    isSelected = false,
                    onClick = {}
                    , name = "Events"
                )
                ToolbarItem(
                    icon = Icons.Outlined.MailOutline,
                    isSelected = false,
                    hasBadge = true,
                    badgeCount = 2,
                    onClick = {},
                    name = "Messages"
                )
                ToolbarItem(
                    icon = Icons.Outlined.Person,
                    isSelected = false,
                    onClick = {},
                    name = "Profile"
                )
            }
        }
    }
}

@Composable
private fun ToolbarItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    hasBadge: Boolean = false,
    badgeCount: Int = 0,
    name: String = ""
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.White.copy(alpha = 0.15f) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            BadgedBox(
                badge = {
                    if (hasBadge) {
                        Badge(
                            containerColor = Color(0xFFD93025),
                            contentColor = Color.White,
                            modifier = Modifier.offset(x = (-2).dp, y = 2.dp)
                        ) {
                            Text(badgeCount.toString(), fontSize = 10.sp)
                        }
                    }
                }
            ) {
                /*
                 colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1A73E8),
                    selectedTextColor = Color(0xFF1A73E8),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                 */
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color(0xFF1A73E8) else Color.Gray,
                    modifier = Modifier.size(26.dp)
                )
            }
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(Color.White, CircleShape)
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) Color(0xFF1A73E8) else Color.Gray
            )
        }
    }
}
