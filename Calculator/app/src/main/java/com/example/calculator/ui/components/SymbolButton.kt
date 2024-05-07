package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.CalculatorApp
import com.example.calculator.CalculatorViewModel

@Composable
fun SymbolButton(
    iconId: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    onClick: () -> Unit
){
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(40.dp)
            .then(modifier)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            modifier = Modifier
                .then(modifier),
            tint = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp3() {
    CalculatorApp(CalculatorViewModel(), openCamera = {})
}