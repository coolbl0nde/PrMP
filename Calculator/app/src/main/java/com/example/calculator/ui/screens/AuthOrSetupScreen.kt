package com.example.calculator.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.calculator.SetupViewModel

@Composable
fun AuthOrSetupScreen(navController: NavHostController, viewModel: SetupViewModel) {
    val isPassKeySet by viewModel.isPassKeySet.collectAsState(initial = false)

    if (isPassKeySet) {
        AuthScreen(navController, viewModel)
    } else {
        SetupScreen(navController, viewModel)
    }
}