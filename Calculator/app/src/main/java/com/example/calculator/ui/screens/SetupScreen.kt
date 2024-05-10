package com.example.calculator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.calculator.SetupViewModel

@Composable
fun SetupScreen(navController: NavHostController, viewModel: SetupViewModel) {
    //val viewModel: SetupViewModel = viewModel()
    var passKey by remember { mutableStateOf("") }
    var confirmPassKey by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Setup Your Pass Key")
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = passKey,
            onValueChange = { passKey = it },
            label = { Text("Enter Pass Key") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassKey,
            onValueChange = { confirmPassKey = it },
            label = { Text("Confirm Pass Key") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        errorMessage?.let {
            Text(it, color = Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (passKey.isNotEmpty() && passKey == confirmPassKey) {
                    viewModel.setPassKey(passKey)
                    navController.navigate("adaptiveCalculatorUI")
                } else {
                    errorMessage = "Pass Key does not match or is empty"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
