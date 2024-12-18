package com.dra.speakeaseapppatient.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import com.dra.speakeaseapppatient.model.LocalizedStrings
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import com.dra.speakeaseapppatient.viewmodel.SpeakViewModel
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SpeakScreen(
    textToSpeechHelper: TextToSpeechHelper,
    viewModel: SpeakViewModel = viewModel(),
    selectedLocale: MutableState<Locale>
) {
    val textInput by viewModel.textInput.collectAsState()
    val history by viewModel.history.collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }
    val speakText: List<String> = LocalizedStrings.getSpeakText(selectedLocale.value)

    val database = FirebaseDatabase.getInstance("https://speakease-eb1ab-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val userRef = database.getReference("profile/history")

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
                label = { Text(speakText[0]) },
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

                    val timestamp = System.currentTimeMillis()
                    val formattedTimestamp = formatTimestamp(timestamp)
                    val historyItem = mapOf(
                        "text" to textInput,
                        "timestamp" to formattedTimestamp
                    )
                    userRef.push().setValue(historyItem)
                        .addOnSuccessListener {
                            Log.d("SpeakScreen", "History saved successfully.")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("SpeakScreen", "Error saving history: ${exception.message}")
                        }

                    viewModel.updateText("")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(speakText[1])
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
                            text = speakText[2],
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
            currentLocale = selectedLocale.value,
            onLanguageSelected = { locale ->
                textToSpeechHelper.setLanguage(locale)
                selectedLocale.value = locale
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
