package com.dra.speakeaseapppatient.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BicaraViewModel : ViewModel() {
    private val _textInput = MutableStateFlow("")
    val textInput: StateFlow<String> get() = _textInput

    fun updateText(newText: String) {
        _textInput.value = newText
    }
}
