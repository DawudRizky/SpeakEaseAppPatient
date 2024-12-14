package com.dra.speakeaseapppatient

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.ComponentActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Enable the splash screen
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Simulate initialization or delay
        splashScreen.setKeepOnScreenCondition { true }

        // Navigate to the main activity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
