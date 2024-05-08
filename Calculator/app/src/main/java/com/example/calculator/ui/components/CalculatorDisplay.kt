package com.example.calculator.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.screens.CalculatorApp
import com.example.calculator.CalculatorViewModel
import com.example.calculator.services.OperationStorageService
import com.example.calculator.ui.theme.Gray

@Composable
fun CalculatorDisplay(value: String, modifier: Modifier = Modifier){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            /*.padding(top = 50.dp, bottom = 120.dp)*/,
        contentAlignment = Alignment.TopEnd,

    ){
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(end = 10.dp),
            color = Gray
        )
    }
}