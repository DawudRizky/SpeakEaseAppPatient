package com.dra.speakeaseapppatient.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dra.speakeaseapppatient.R
import com.dra.speakeaseapppatient.model.LocalizedStrings
import com.dra.speakeaseapppatient.ui.components.IconTextButton
import com.dra.speakeaseapppatient.utils.TextToSpeechHelper
import com.dra.speakeaseapppatient.viewmodel.PainViewModel
import com.dra.speakeaseapppatient.viewmodel.ProfileViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PainScreen(
    textToSpeechHelper: TextToSpeechHelper,
    painViewModel: PainViewModel = viewModel(factory = PainViewModelFactory(textToSpeechHelper)),
    profileViewModel: ProfileViewModel = viewModel(),
    selectedLocale: MutableState<Locale>
) {
    val selectedTabIndex by painViewModel.selectedTabIndex
    val tabs: List<String> = LocalizedStrings.getPainTabs(selectedLocale.value)

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
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(0.dp),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { painViewModel.selectedTabIndex.value = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> Level(painViewModel, profileViewModel, selectedLocale)
                1 -> Location(painViewModel)
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

@Suppress("UNCHECKED_CAST")
class PainViewModelFactory(
    private val textToSpeechHelper: TextToSpeechHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PainViewModel::class.java)) {
            return PainViewModel(textToSpeechHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun Level(painViewModel: PainViewModel, profileViewModel: ProfileViewModel, selectedLocale: MutableState<Locale>) {
    val buttonItems = LocalizedStrings.getPainButtonLabels(selectedLocale.value)

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(buttonItems) { (iconRes, description) ->
                IconTextButton(
                    iconRes = iconRes,
                    text = description,
                    onClick = {
                        painViewModel.onButtonClicked(description)

                        val timestamp = System.currentTimeMillis()
                        profileViewModel.saveHistoryItem(description, timestamp)
                    }
                )
            }
        }
    }
}

@Composable
fun Location(viewModel: PainViewModel) {
    val tappedPosition by viewModel.tappedPosition

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    viewModel.onImageTapped(offset)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pain_body),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
        )

        tappedPosition?.let { position ->
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.Red,
                    radius = 16.dp.toPx(),
                    center = position
                )
            }
        }
    }
}
