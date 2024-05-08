package com.example.calculator.data

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ButtonConfig(
    val text: String,
    val onClick: () -> Unit,
    val fontSize: TextUnit = 30.sp,
    val weight: Float = 1f,
    val modifier: Modifier = Modifier
)