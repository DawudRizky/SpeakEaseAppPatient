package com.dra.speakeaseapppatient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.util.Locale

@Composable
fun LanguagePickerDialog(
    onLanguageSelected: (Locale) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        Locale.ENGLISH,
        Locale.JAPANESE,
        Locale("id"),
        Locale.FRENCH,
        Locale("es"),
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Language") },
        text = {
            Column {
                languages.forEach { locale ->
                    TextButton(
                        onClick = {
                            onLanguageSelected(locale)
                        }
                    ) {
                        Text(locale.displayLanguage)
                    }
                }
            }
        },
        confirmButton = {}
    )
}