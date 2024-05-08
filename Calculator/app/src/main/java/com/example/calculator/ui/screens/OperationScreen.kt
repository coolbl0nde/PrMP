package com.example.calculator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calculator.services.OperationStorageService
import com.example.calculator.data.CalculatorOperation
import kotlinx.coroutines.launch

@Composable
fun OperationScreen(navController: NavController) {
    val operations = remember { mutableStateListOf<CalculatorOperation>() }
    val coroutineScope = rememberCoroutineScope()
    val operationStorageService = OperationStorageService()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            operationStorageService.getOperations(
                onSuccess = { ops ->
                    operations.clear()
                    operations.addAll(ops)
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)
                .weight(1f)
                .fillMaxHeight()
        ) {

            for (operation in operations) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = operation.input,
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "= ${operation.result}",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    operationStorageService.deleteOperations(
                        onSuccess = {
                            operations.clear()
                        },
                        onFailure = {}
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "Очистить историю")
            }

            Button(
                onClick = {
                    navController.navigate("adaptiveCalculatorUI")
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "Выход")
            }
        }
    }
}

