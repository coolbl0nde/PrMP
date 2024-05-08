package com.example.calculator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.calculator.services.OperationStorageService
import com.example.calculator.data.CalculatorOperation
import kotlinx.coroutines.launch

@Composable
fun OperationsScreen() {
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

    Box(modifier = Modifier.fillMaxSize()) {

        Column {

            //TopAppBar(title = { /*TODO*/ })

            for (operation in operations){
                Text(
                    "Input: ${operation.input}, Result: ${operation.result}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Button(
            onClick = { operationStorageService.deleteOperations(
                onSuccess = {
                    operations.clear()
                },
                onFailure = {}
            ) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Очистить историю")
        }
    }

}
