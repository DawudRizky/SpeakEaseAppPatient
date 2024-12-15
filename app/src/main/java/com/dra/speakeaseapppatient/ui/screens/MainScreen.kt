package com.dra.speakeaseapppatient.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseapppatient.navigation.NavRoute
import com.dra.speakeaseapppatient.ui.components.BottomNavBar
import com.dra.speakeaseapppatient.ui.components.TopBar
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper

@Composable
fun MainScreen(
    textToSpeechHelper: TextToSpeechHelper,
    onNavigateToEmergency: () -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(onEmergencyClick = onNavigateToEmergency) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Speak.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoute.Speak.route) { SpeakScreen(textToSpeechHelper = textToSpeechHelper) }
            composable(NavRoute.Pain.route) { PainScreen(textToSpeechHelper) }
            composable(NavRoute.Need.route) { NeedScreen(textToSpeechHelper) }
            composable(NavRoute.Person.route) { PersonScreen(textToSpeechHelper) }
            composable(NavRoute.Profile.route) { ProfileScreen(onLogout = onLogout) }
        }
    }
}