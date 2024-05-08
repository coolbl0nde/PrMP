package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.calculator.ui.screens.AdaptiveCalculatorUI
import com.example.calculator.ui.theme.CalculatorTheme
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.calculator.services.OperationStorageService
import com.example.calculator.services.ThemeStorageService
import com.example.calculator.ui.screens.OperationsScreen

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

    @Composable
    fun MyApp(
        modifier: Modifier = Modifier,
        themeViewModel: ThemeViewModel
    ) {
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

        val openCamera = {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
        val closeCamera = { shouldShowCamera = false }

        val operationStorageService = OperationStorageService()


        Surface(modifier) {
            if (shouldShowCamera) {
                //CameraPreviewScreen(viewModel, closeCamera)
                OperationsScreen()
            } else {
                AdaptiveCalculatorUI(
                    viewModel,
                    openCamera,
                    themeViewModel
                )
            }
        }
    }
}