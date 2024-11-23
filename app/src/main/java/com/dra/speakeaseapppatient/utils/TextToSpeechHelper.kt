package com.dra.speakeaseapppatient.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context) {

    private lateinit var textToSpeech: TextToSpeech  // Use lateinit for deferred initialization

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.getDefault() // Initialize language once TTS is ready
            }
        }
    }

//    fun speak(text: String) {
//        if (::textToSpeech.isInitialized) {  // Check if textToSpeech is initialized
//            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
//        }
//    }
//
//    fun shutdown() {
//        if (::textToSpeech.isInitialized) {
//            textToSpeech.shutdown()
//        }
//    }
    fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun setLanguage(locale: Locale) {
        val result = textToSpeech.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TextToSpeechHelper", "Language not supported: ${locale.displayLanguage}")
        }
    }

    fun shutdown() {
        textToSpeech.shutdown()
    }
}