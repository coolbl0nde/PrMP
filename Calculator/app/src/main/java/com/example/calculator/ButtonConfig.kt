package com.example.calculator

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ButtonConfig(
    val text: String,
    val onClick: () -> Unit,
    val fontSize: TextUnit = 30.sp
)