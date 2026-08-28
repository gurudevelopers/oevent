package com.sendmystatus.oeventapp.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendmystatus.oeventapp.ui.Route
import com.sendmystatus.oeventapp.ui.UserType

@Composable
@Preview
fun AppPreview() {
    DemoQuickAccess(
        onNavigate = {}
    )
}

@Composable
fun DemoQuickAccess(onNavigate: (Route) -> Unit) {

    val list = listOf(
        "Guest Login",
        "Merchant Login",
        "Merchant Create Account",
        "Business Details",
        "Business Event setup",
        "Merchant DashBoard",
        "Event Template",
        "Event creation",
        "Event Setting",
        "Profile",
        "Program Event creation",
        "Event Roles",
        "Event Dashboard"
    )
    val navigationList = listOf(
        Route.Login,
        Route.Merchant,
        Route.BusinessDetail,
        Route.BusinessEventSetup,
        Route.Dashboard(UserType.MERCHANT),
        Route.EventTemplate,
        Route.EventTemplate,
        Route.EventCreate("Event Template 1"),
        Route.EventSetting("Event Template 1", "Event Template 1"),
        Route.Profile,
        Route.EventProgramAdd,
        Route.Scanner,
        Route.Reward,
    )

    val actionRouter = list.zip(navigationList)
    println("actionRouter: ${actionRouter::class}")
    println("actionRouter: $actionRouter")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quick Access",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            actionRouter.forEach {
                item {
                    Card(
                        modifier = Modifier.size(128.dp, 120.dp),
                        onClick = {
                            println("got to ${it.second}")

                            onNavigate(it.second as Route)

                        }) {
                        Text(
                            text = it.first,
                            modifier = Modifier.fillMaxSize().padding(20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            /* navigationList.forEach {
                 item {
                     ElevatedButton(onClick = {
                         println("got to ${it}")

                         onNavigate(it as Route)

                     }) {
                         Text(text = it.toString())
                     }
                 }
             }*/
            /*list.forEach {
                item {
                    ElevatedButton(onClick = { *//*TODO*//* }) {
                        Text(text = it)
                    }
                }
            }*/
        }

    }


}