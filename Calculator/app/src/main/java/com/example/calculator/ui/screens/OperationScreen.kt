package com.example.calculator.ui.screens

import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.OperationStorageService
import com.example.calculator.data.CalculatorOperation
import kotlinx.coroutines.launch

@Composable
fun OperationsScreen(firestoreRepository: OperationStorageService) {
    val operations = remember { mutableStateListOf<CalculatorOperation>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            firestoreRepository.getOperations(
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
            onClick = { firestoreRepository.deleteOperations(
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
