package com.dra.speakeaseapppatient.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SpeakViewModel : ViewModel() {
    private val _textInput = MutableStateFlow("")
    val textInput: StateFlow<String> = _textInput

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history

    fun updateText(newText: String) {
        _textInput.value = newText
    }

    fun addToHistory(input: String) {
        if (input.isNotBlank()) {
            _history.value = listOf(input) + _history.value.take(9)
        }
    }
}
