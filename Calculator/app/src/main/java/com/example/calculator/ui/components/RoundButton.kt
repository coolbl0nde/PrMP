package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.screens.CalculatorApp
import com.example.calculator.CalculatorViewModel
import com.example.calculator.ui.theme.Pink80

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Pink80,
    fontSize: TextUnit
){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(color)
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            fontSize = fontSize
        )
    }
    /*Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(2.dp)
            .then(modifier),
    ) {
        Text(
            text = text,
            fontSize = fontSize,

        )
    }*/
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    CalculatorApp(CalculatorViewModel(), openCamera = {})
}