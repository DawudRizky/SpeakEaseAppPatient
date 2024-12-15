package com.dra.speakeaseapppatient.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseapppatient.navigation.NavRoute
import com.dra.speakeaseapppatient.ui.components.BottomNavBar
import com.dra.speakeaseapppatient.ui.components.TopBar
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import java.util.Locale

@Composable
fun MainScreen(
    textToSpeechHelper: TextToSpeechHelper,
    onNavigateToEmergency: () -> Unit,
    onLogout: () -> Unit
) {
    val selectedLocale = remember { mutableStateOf(Locale.ENGLISH) }
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
            composable(NavRoute.Speak.route) {
                SpeakScreen(textToSpeechHelper = textToSpeechHelper, selectedLocale = selectedLocale)
            }
            composable(NavRoute.Pain.route) {
                PainScreen(textToSpeechHelper, selectedLocale = selectedLocale)
            }
            composable(NavRoute.Need.route) {
                NeedScreen(
                    textToSpeechHelper = textToSpeechHelper,
                    selectedLocale = selectedLocale
                )
            }
            composable(NavRoute.Person.route) {
                PersonScreen(textToSpeechHelper, selectedLocale = selectedLocale)
            }
            composable(NavRoute.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}
