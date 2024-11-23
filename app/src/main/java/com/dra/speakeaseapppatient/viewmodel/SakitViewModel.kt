package com.dra.speakeaseapppatient.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dra.speakeaseapppatient.R
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import kotlinx.coroutines.launch

class SakitViewModel(
    private val textToSpeechHelper: TextToSpeechHelper
) : ViewModel() {

    val selectedTabIndex: MutableState<Int> = mutableStateOf(0)

    val tappedPosition: MutableState<Offset?> = mutableStateOf(null)

    private val buttonItems = listOf(
        Pair(R.drawable.baseline_healing_24, "Home"),
        Pair(R.drawable.baseline_healing_24, "Favorite"),
        Pair(R.drawable.baseline_healing_24, "Star"),
        Pair(R.drawable.baseline_healing_24, "Settings"),
        Pair(R.drawable.baseline_healing_24, "Search"),
        Pair(R.drawable.baseline_healing_24, "Profile")
    )

    fun getButtonItems() = buttonItems

    fun onButtonClicked(description: String) {
        viewModelScope.launch {
            textToSpeechHelper.speak(description)
        }
    }

    fun onImageTapped(offset: Offset) {
        tappedPosition.value = offset
    }
}
