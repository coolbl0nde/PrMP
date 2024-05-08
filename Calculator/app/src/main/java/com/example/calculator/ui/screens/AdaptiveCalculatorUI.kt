package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.calculator.CalculatorViewModel
import com.example.calculator.OperationStorageService

@Composable
fun AdaptiveCalculatorUI(
    viewModel: CalculatorViewModel,
    openCamera: () -> Unit,
    firestoreRepository: OperationStorageService
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeUI(viewModel, openCamera)
    } else {
        CalculatorApp(viewModel, openCamera, firestoreRepository)
    }
}