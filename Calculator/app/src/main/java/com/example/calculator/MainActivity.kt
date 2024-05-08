package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.calculator.ui.screens.AdaptiveCalculatorUI
import com.example.calculator.ui.theme.CalculatorTheme
import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.services.OperationStorageService
import com.example.calculator.services.ThemeStorageService
import com.example.calculator.ui.components.TopAppBar
import com.example.calculator.ui.screens.CameraScreen
import com.example.calculator.ui.screens.OperationScreen

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
        modifier: Modifier = Modifier,
        themeViewModel: ThemeViewModel
    ) {
        val navController = rememberNavController()
        val viewModel = CalculatorViewModel()

        var shouldShowCamera by remember { mutableStateOf(false) }
        val cameraPermissionRequest = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    shouldShowCamera = true
                }
            }
        )


        Scaffold( topBar = { TopAppBar(themeViewModel) }){
            NavHost(navController = navController, startDestination = "adaptiveCalculatorUI") {
                composable("adaptiveCalculatorUI") {
                    AdaptiveCalculatorUI(
                        navController,
                        themeViewModel = themeViewModel,
                        viewModel = viewModel
                    )
                }
                composable("cameraScreen") {
                    cameraPermissionRequest.launch(Manifest.permission.CAMERA)

                    if (shouldShowCamera){
                        CameraScreen(viewModel, navController)
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
            }
        }

    }
}