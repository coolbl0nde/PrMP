package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.calculator.ui.screens.AdaptiveCalculatorUI
import com.example.calculator.ui.theme.CalculatorTheme
import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.ui.components.TopAppBar
import com.example.calculator.ui.screens.AuthOrSetupScreen
import com.example.calculator.ui.screens.AuthScreen
import com.example.calculator.ui.screens.CameraScreen
import com.example.calculator.ui.screens.OperationScreen
import com.example.calculator.ui.screens.SetupScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel = ThemeViewModel()

            CalculatorTheme(themeViewModel = themeViewModel) {
                MyApp(themeViewModel = themeViewModel)
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MyApp(
        themeViewModel: ThemeViewModel
    ) {
        val navController = rememberNavController()
        val calculatorViewModel = CalculatorViewModel()

        var shouldShowCamera by remember { mutableStateOf(false) }
        val cameraPermissionRequest = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    shouldShowCamera = true
                }
            }
        )

        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val setupViewModel: SetupViewModel = viewModel()

        Scaffold(
            topBar = {
                if (!isLandscape){
                    TopAppBar(themeViewModel)
                }
            }
        ){
            NavHost(navController = navController, startDestination = "setupScreen") {
                composable("setupScreen") {
                    AuthOrSetupScreen(
                        navController,
                        setupViewModel
                    )
                }

                composable("adaptiveCalculatorUI") {
                    AdaptiveCalculatorUI(
                        navController,
                        themeViewModel = themeViewModel,
                        viewModel = calculatorViewModel,
                        setupViewModel = setupViewModel
                    )
                }
                composable("cameraScreen") {
                    cameraPermissionRequest.launch(Manifest.permission.CAMERA)

                    if (shouldShowCamera){
                        CameraScreen(calculatorViewModel, navController)
                    }
                }
                /*composable("landscapeCalculatorScreen") {
                    LandscapeCalculatorScreen(navController)
                }
                composable("portraitCalculatorScreen") {
                    PortraitCalculatorScreen(navController)
                }*/
                composable("operationScreen") {
                    OperationScreen(navController)
                }

                composable("setupScreen") {
                    SetupScreen(
                        navController,
                        setupViewModel
                    )
                }
            }
        }

    }
}