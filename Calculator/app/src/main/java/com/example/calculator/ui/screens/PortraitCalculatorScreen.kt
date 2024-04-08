package com.example.calculator.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ButtonConfig
import com.example.calculator.CalculatorViewModel
import com.example.calculator.R
import com.example.calculator.ui.components.CalculatorDisplay
import com.example.calculator.ui.components.RoundButton
import com.example.calculator.ui.components.SymbolButton
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.ui.unit.sp


@Composable
fun CalculatorApp(viewModel: CalculatorViewModel = CalculatorViewModel()){

    val buttons = listOf(
        ButtonConfig(text = "C", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "( )", onClick = { viewModel.onSymbolClicked("()") }),
        ButtonConfig(text = "%", onClick = { viewModel.onSymbolClicked("%") }),
        ButtonConfig(text = "÷", onClick = { viewModel.onSymbolClicked("/") },
            fontSize = 40.sp),
        ButtonConfig(text = "7", onClick = { viewModel.onSymbolClicked("7") }),
        ButtonConfig(text = "8", onClick = { viewModel.onSymbolClicked("8") }),
        ButtonConfig(text = "9", onClick = { viewModel.onSymbolClicked("9") }),
        ButtonConfig(text = "×", onClick = { viewModel.onSymbolClicked("*") },
            fontSize = 40.sp),
        ButtonConfig(text = "4", onClick = { viewModel.onSymbolClicked("4") }),
        ButtonConfig(text = "5", onClick = { viewModel.onSymbolClicked("5") }),
        ButtonConfig(text = "6", onClick = { viewModel.onSymbolClicked("6") }),
        ButtonConfig(text = "—", onClick = { viewModel.onSymbolClicked("-") }),
        ButtonConfig(text = "1", onClick = { viewModel.onSymbolClicked("1") }),
        ButtonConfig(text = "2", onClick = { viewModel.onSymbolClicked("2") }),
        ButtonConfig(text = "3", onClick = { viewModel.onSymbolClicked("3") }),
        ButtonConfig(text = "+", onClick = { viewModel.onSymbolClicked("+") }),
        ButtonConfig(text = "+/–", onClick = { viewModel.onSymbolClicked("+/-") }),
        ButtonConfig(text = "0", onClick = { viewModel.onSymbolClicked("0") }),
        ButtonConfig(text = ",", onClick = { viewModel.onSymbolClicked(".") }),
        ButtonConfig(text = "=", onClick = { viewModel.onEqualClicked() }),
    )

    val rows = buttons.chunked(4)

    var currentUI by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier.fillMaxHeight()
    ) {

        CalculatorDisplay(
            value = viewModel.userInput,
            modifier = Modifier.padding(top = 50.dp, bottom = 120.dp)
        )

        Text(text = viewModel.output)

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SymbolButton(
                iconId = R.drawable.outline_calculate,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    currentUI = "Landscape"
                }
            )

            /*if (currentUI == "Landscape") {
                val context = LocalContext.current

                (context as? Activity)?.let {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
                LandscapeUI()
                return
            }*/

            Spacer(Modifier.weight(2f))

            SymbolButton(
                iconId = R.drawable.outline_backspace,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    viewModel.onBackspaceClicked()
                }
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(10.dp)
        )

        rows.forEach { row ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { button ->
                    RoundButton(
                        text = button.text,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable { button.onClick() },
                        fontSize = button.fontSize
                    )

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    CalculatorApp(CalculatorViewModel())
}