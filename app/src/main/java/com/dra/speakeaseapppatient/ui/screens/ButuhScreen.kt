package com.dra.speakeaseapppatient.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dra.speakeaseapppatient.R
import com.dra.speakeaseapppatient.ui.components.IconTextButton
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ButuhScreen(textToSpeechHelper: TextToSpeechHelper) {
    val buttonItems = listOf(
        Pair(R.drawable.baseline_healing_24, "Home"),
        Pair(R.drawable.baseline_healing_24, "Favorite"),
        Pair(R.drawable.baseline_healing_24, "Star"),
        Pair(R.drawable.baseline_healing_24, "Settings"),
        Pair(R.drawable.baseline_healing_24, "Search"),
        Pair(R.drawable.baseline_healing_24, "Profile")
    )
    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showLanguageDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Select Language"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp), // Minimum button size
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(buttonItems) { (iconRes, description) ->
                IconTextButton(
                    iconRes = iconRes,
                    text = description,
                    onClick = { textToSpeechHelper.speak(description) }
                )
            }
        }
    }

    if (showLanguageDialog) {
        LanguagePickerDialog(
            onLanguageSelected = { locale ->
                textToSpeechHelper.setLanguage(locale)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}
