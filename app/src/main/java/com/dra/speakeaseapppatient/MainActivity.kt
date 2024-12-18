package com.dra.speakeaseapppatient

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseapppatient.navigation.NavRoute
import com.dra.speakeaseapppatient.ui.screens.EmergencyScreen
import com.dra.speakeaseapppatient.ui.screens.LoginScreen
import com.dra.speakeaseapppatient.ui.screens.CoreScreen
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeechHelper: TextToSpeechHelper
    private var backPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        textToSpeechHelper = TextToSpeechHelper(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val auth = FirebaseAuth.getInstance()
            val isLoggedIn = auth.currentUser != null

            val selectedLocale = remember { mutableStateOf(Locale.ENGLISH) }

            NavHost(
                navController = navController,
                startDestination = if (isLoggedIn) "main" else "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("main") {
                    CoreScreen(
                        textToSpeechHelper = textToSpeechHelper,
                        selectedLocale = selectedLocale,
                        onNavigateToEmergency = {
                            navController.navigate(NavRoute.Emergency.route)
                        },
                        onLogout = {
                            auth.signOut()
                            navController.navigate("login") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    )
                }

                composable(NavRoute.Emergency.route) {
                    EmergencyScreen(
                        selectedLocale = selectedLocale,
                        onExit = {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }

    @Deprecated("This method has been deprecated")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (backPressedOnce) {
            finishAffinity()
            return
        }

        backPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        handler.postDelayed({ backPressedOnce = false }, 2000)
    }
}