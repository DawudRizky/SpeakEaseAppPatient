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
        Pair(R.drawable.person_raised_hand_96dp_ffffff_fill0_wght400_grad0_opsz48, "Tolong"),
        Pair(R.drawable.water_full_96dp_ffffff_fill0_wght400_grad0_opsz48, "Minum"),
        Pair(R.drawable.fork_spoon_96dp_ffffff_fill0_wght400_grad0_opsz48, "Makan"),
        Pair(R.drawable.pill_96dp_ffffff_fill0_wght400_grad0_opsz48, "Obat"),
        Pair(R.drawable.wc_96dp_ffffff_fill0_wght400_grad0_opsz48, "Kamar Mandi"),
        Pair(R.drawable.luggage_96dp_ffffff_fill0_wght400_grad0_opsz48, "Barang")
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
