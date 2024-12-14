package com.dra.speakeaseapppatient

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dra.speakeaseapppatient.navigation.NavRoute
import com.dra.speakeaseapppatient.ui.screens.EmergencyScreen
import com.dra.speakeaseapppatient.ui.screens.LoginScreen
import com.dra.speakeaseapppatient.ui.screens.MainScreen
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import com.google.firebase.auth.FirebaseAuth

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
                    MainScreen(
                        textToSpeechHelper = textToSpeechHelper,
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
                    EmergencyScreen(onExit = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    )
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (backPressedOnce) {
            finishAffinity() // Terminates the app
            return
        }

        backPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        // Reset the flag after 2 seconds
        handler.postDelayed({ backPressedOnce = false }, 2000)
    }
}
