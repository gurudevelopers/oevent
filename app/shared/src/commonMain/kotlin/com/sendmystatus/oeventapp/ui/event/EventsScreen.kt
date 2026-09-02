package com.sendmystatus.oeventapp.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.sendmystatus.oeventapp.data.model.event.Event
import com.sendmystatus.oeventapp.ui.viewmodel.EventsViewModel
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    onCreateEvent: () -> Unit,
    viewModel: EventsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Events",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        color = Color(0xFF1A1C1E)
                    )
                },
                actions = {
                    Surface(
                        onClick = onCreateEvent,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF5E35B1)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Create Event",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },

        ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(padding)
//                .background(Color.White)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(uiState.categories) { index, category ->
                    val isSelected = uiState.selectedCategoryIndex == index
                    Surface(
                        onClick = { viewModel.updateSelectedCategory(index) },
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF673AB7) else Color(0xFFEEEEEE)
                        )
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
                            color = if (isSelected) Color(0xFF673AB7) else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                val currentEvents =
                    if (uiState.selectedTabIndex == 0) uiState.upcomingEvents else uiState.pastEvents

                if (currentEvents.isEmpty()) {
                    EmptyEventsSection(onCreateEvent)
                } else {
                    currentEvents.forEach { event ->
                        EventListItem(event)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}

@Composable
fun EmptyEventsSection(onCreateEvent: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Illustration: Calendar with Magnifying Glass
        Box(
            modifier = Modifier.size(280.dp, 200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Calendar background
            Card(
                modifier = Modifier.size(120.dp, 140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFF0F0F0))
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(18.dp)
                            .background(Color(0xFFE8EAF6))
                    )
                    Column(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(4) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                repeat(3) {
                                    Box(
                                        modifier = Modifier.size(14.dp)
                                            .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Decorative elements
            Box(
                modifier = Modifier.size(30.dp).offset(x = (-80).dp, y = (-20).dp)
                    .background(Color(0xFFEDE7F6), CircleShape)
            )
            Box(
                modifier = Modifier.size(40.dp).offset(x = 90.dp, y = (-40).dp)
                    .background(Color(0xFFF3E5F5), CircleShape)
            )

            // Magnifying Glass
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = 60.dp, y = 50.dp),
                tint = Color(0xFF673AB7).copy(alpha = 0.8f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No public events yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1C1E)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "There are no public events available right now.\nYou can be the first to create an event\nor stay tuned for upcoming events.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Action Cards
        ActionCardSimple(
            title = "Create Your First Event",
            description = "Share your idea, invite people,\nand build something amazing.",
            icon = Icons.Outlined.EventAvailable,
            iconColor = Color(0xFF673AB7),
            iconBgColor = Color(0xFFF3E8FA),
            onClick = onCreateEvent
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionCardSimple(
            title = "Explore Invite-Only Events",
            description = "Join events using an invite link\nor event code.",
            icon = Icons.Default.Search,
            iconColor = Color(0xFF2E7D32),
            iconBgColor = Color(0xFFE8F5E9),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {


            // Get Started Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    //.background(Color(0xFFFAFAFA), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SmallGetStartedItem(
                        modifier = Modifier.weight(1f),
                        title = "Invite People",
                        description = "Invite friends to\ncollaborate on events",
                        icon = Icons.Default.Groups,
                        onClick = {}
                    )

                    // Vertical divider
                    Box(
                        modifier = Modifier.width(1.dp).height(60.dp).background(Color(0xFFEEEEEE))
                            .align(Alignment.CenterVertically)
                    )

                    SmallGetStartedItem(
                        modifier = Modifier.weight(1f),
                        title = "My Businesses",
                        description = "Manage your\nbusiness profiles",
                        icon = Icons.Default.Storefront,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ActionCardSimple(
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color,
    iconBgColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(iconBgColor, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color(0xFF673AB7)
            )
        }
    }
}

@Composable
fun SmallGetStartedItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Color(0xFFF3E8FA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF673AB7),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                lineHeight = 16.sp,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun EventListItem(event: Event) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = event.type,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF0E7FF),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = if (event.isPublic) "Public" else "Private",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF673AB7),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CalendarMonth,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                val date = event.startDateAndTime
                Text(
                    text = "${date.day} ${date.month.name.take(3)}, ${date.year} • ${date.hour}:${
                        date.minute.toString().padStart(2, '0')
                    }",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${event.venueName}, ${event.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun EventsScreenPreview() {
    EventsScreen(
        onCreateEvent = {},
        viewModel = EventsViewModel()
    )
}
