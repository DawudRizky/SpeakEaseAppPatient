package com.dra.speakeaseapppatient.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Locale

@Composable
fun LanguagePickerDialog(
    currentLocale: Locale,
    onLanguageSelected: (Locale) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        Locale.ENGLISH,
        Locale("id"),
    )

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Select Language",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                languages.forEach { locale ->
                    val isSelected = locale == currentLocale
                    TextButton(
                        onClick = {
                            onLanguageSelected(locale)
                            onDismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        ),

                    ) {
                        Text(
                            text = locale.displayLanguage,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}



