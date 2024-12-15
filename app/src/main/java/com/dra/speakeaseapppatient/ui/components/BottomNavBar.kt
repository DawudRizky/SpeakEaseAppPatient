package com.dra.speakeaseapppatient.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dra.speakeaseapppatient.R
import com.dra.speakeaseapppatient.model.BottomNavItem

val items = listOf(
    BottomNavItem("speak", "Speak", R.drawable.nav_speak),
    BottomNavItem("pain", "Pain", R.drawable.nav_pain),
    BottomNavItem("need", "Need", R.drawable.nav_need),
    BottomNavItem("person", "Person", R.drawable.nav_person),
    BottomNavItem("profile", "Profile", R.drawable.nav_profile)
)

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar {
        val currentDestination by navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}