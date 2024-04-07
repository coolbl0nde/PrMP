package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.calculator.CalculatorViewModel

@Composable
fun AdaptiveCalculatorUI() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeUI(CalculatorViewModel())
    } else {
        CalculatorApp(CalculatorViewModel())
    }
}