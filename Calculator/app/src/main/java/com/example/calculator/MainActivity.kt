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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.calculator.ui.screens.CameraPreviewScreen

class MainActivity : ComponentActivity() {

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Implement camera related  code
                setContent {
                    CalculatorTheme {
                        MyApp()
                    }
                }
            } else {
                // Camera permission denied (Handle denied operation)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                setContent {
                    CalculatorTheme {
                        MyApp()
                    }
                }
            }

            else -> {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }

        }
    }

    @Composable
    fun MyApp(modifier: Modifier = Modifier) {
        var viewModel = CalculatorViewModel()

        var shouldShowCamera by remember { mutableStateOf(false) }

        val openCamera = { shouldShowCamera = true }
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