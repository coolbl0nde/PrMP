package com.example.calculator

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.calculator.ui.screens.AdaptiveCalculatorUI
import com.example.calculator.ui.theme.CalculatorTheme
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.calculator.ui.screens.CameraPreviewScreen
import com.example.calculator.ui.theme.LocalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val theme = remember { mutableStateOf("light") }
            CompositionLocalProvider(LocalTheme provides theme) {
                CalculatorTheme() {
                    MyApp()
                }
            }
        }
    }

    @Composable
    fun MyApp(modifier: Modifier = Modifier) {
        var viewModel = CalculatorViewModel()

        var themeOption by remember { mutableStateOf("light") }

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

        Surface(modifier) {
            if (shouldShowCamera) {
                CameraPreviewScreen(viewModel, closeCamera)
            } else {
                AdaptiveCalculatorUI(viewModel, openCamera)
            }
        }
    }
}