package com.dra.speakeaseapppatient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dra.speakeaseapppatient.ui.screens.MainScreen
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeechHelper: TextToSpeechHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        textToSpeechHelper = TextToSpeechHelper(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(textToSpeechHelper)
        }
    }
}