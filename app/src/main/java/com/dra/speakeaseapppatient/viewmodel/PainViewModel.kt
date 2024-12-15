package com.dra.speakeaseapppatient.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dra.speakeaseapppatient.R
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import kotlinx.coroutines.launch

class PainViewModel(
    private val textToSpeechHelper: TextToSpeechHelper
) : ViewModel() {

    val selectedTabIndex: MutableState<Int> = mutableIntStateOf(0)

    val tappedPosition: MutableState<Offset?> = mutableStateOf(null)

    private val buttonItems = listOf(
        Pair(R.drawable.pain_0, "Tidak sakit"),
        Pair(R.drawable.pain_2, "Sedikit sakit"),
        Pair(R.drawable.pain_4, "Sedikit lebih sakit"),
        Pair(R.drawable.pain_6, "lebih sakit"),
        Pair(R.drawable.pain_8, "Sangat sakit"),
        Pair(R.drawable.pain_10, "Tersakit")
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