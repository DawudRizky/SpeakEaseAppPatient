package com.dra.speakeaseapppatient.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import com.dra.speakeaseapppatient.viewmodel.BicaraViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SpeakScreen(
    textToSpeechHelper: TextToSpeechHelper,
    viewModel: BicaraViewModel = viewModel(),
    selectedLocale: MutableState<Locale> // Pass the state as MutableState
) {
    val textInput by viewModel.textInput.collectAsState()
    val history by viewModel.history.collectAsState()
    // Current selected locale state
    var currentLocale by remember { mutableStateOf(Locale.getDefault()) }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { viewModel.updateText(it) },
                label = { Text("Enter text") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (textInput.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateText("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear text"
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    textToSpeechHelper.speak(textInput)
                    viewModel.addToHistory(textInput)
                    viewModel.updateText("")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Speak")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "History",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(history.size) { index ->
                            val historyItem = history[index]

                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 0.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { textToSpeechHelper.speak(historyItem) }) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play Text"
                                        )
                                    }

                                    Text(
                                        text = historyItem,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                if (index < history.size - 1) {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp),
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showLanguageDialog) {
        LanguagePickerDialog(
            currentLocale = selectedLocale.value, // Pass the current locale
            onLanguageSelected = { locale ->
                textToSpeechHelper.setLanguage(locale)
                selectedLocale.value = locale
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}
