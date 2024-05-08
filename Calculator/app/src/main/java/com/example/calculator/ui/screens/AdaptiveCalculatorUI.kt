package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.calculator.CalculatorViewModel
import com.example.calculator.ThemeViewModel
import com.example.calculator.services.OperationStorageService

@Composable
fun AdaptiveCalculatorUI(
    navController: NavController,
    viewModel: CalculatorViewModel,
    themeViewModel: ThemeViewModel
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeUI(viewModel)
    } else {
        CalculatorApp(
            viewModel,
            themeViewModel,
            navController
        )
    }
}