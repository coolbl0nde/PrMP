package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.data.ButtonConfig
import com.example.calculator.CalculatorViewModel
import com.example.calculator.R
import com.example.calculator.ui.components.CalculatorDisplay
import com.example.calculator.ui.components.RoundButton
import com.example.calculator.ui.components.SymbolButton

@Composable
fun LandscapeUI(viewModel: CalculatorViewModel = CalculatorViewModel()) {

    val context = LocalContext.current

    val buttons = listOf(
        ButtonConfig(text = "sin", onClick = { viewModel.onSymbolClicked("sin(") }),
        ButtonConfig(text = "cos", onClick = { viewModel.onSymbolClicked("cos(") }),
        ButtonConfig(text = "C", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "( )", onClick = { viewModel.onSymbolClicked("()") }),
        ButtonConfig(text = "%", onClick = { viewModel.onSymbolClicked("%") }),
        ButtonConfig(text = "÷", onClick = { viewModel.onSymbolClicked("/") }),
        ButtonConfig(text = "1/x", onClick = { viewModel.onSymbolClicked("1/") }),
        ButtonConfig(text = "√", onClick = { viewModel.onSymbolClicked("sqrt(") }),
        ButtonConfig(text = "7", onClick = { viewModel.onSymbolClicked("7") }),
        ButtonConfig(text = "8", onClick = { viewModel.onSymbolClicked("8") }),
        ButtonConfig(text = "9", onClick = { viewModel.onSymbolClicked("9") }),
        ButtonConfig(text = "×", onClick = { viewModel.onSymbolClicked("*") }),
        ButtonConfig(text = "ln", onClick = { viewModel.onSymbolClicked("ln(") }),
        ButtonConfig(text = "log", onClick = { viewModel.onSymbolClicked("log(10,") }),
        ButtonConfig(text = "4", onClick = { viewModel.onSymbolClicked("4") }),
        ButtonConfig(text = "5", onClick = { viewModel.onSymbolClicked("5") }),
        ButtonConfig(text = "6", onClick = { viewModel.onSymbolClicked("6") }),
        ButtonConfig(text = "—", onClick = { viewModel.onSymbolClicked("-") }),
        ButtonConfig(text = "x²", onClick = { viewModel.onSymbolClicked("^2") }),
        ButtonConfig(text = "x^y", onClick = { viewModel.onSymbolClicked("^") }),
        ButtonConfig(text = "1", onClick = { viewModel.onSymbolClicked("1") }),
        ButtonConfig(text = "2", onClick = { viewModel.onSymbolClicked("2") }),
        ButtonConfig(text = "3", onClick = { viewModel.onSymbolClicked("3") }),
        ButtonConfig(text = "+", onClick = { viewModel.onSymbolClicked("+") }),
        ButtonConfig(text = "+/–", onClick = { viewModel.onSymbolClicked("+/-") }),
        ButtonConfig(text = "0", onClick = { viewModel.onSymbolClicked("0") }),
        ButtonConfig(text = ",", onClick = { viewModel.onSymbolClicked(".") }),
        ButtonConfig(text = "=", onClick = { viewModel.onEqualClicked(context) }, weight = 2f),
    )

    val rows = buttons.chunked(6)

    Column(modifier = Modifier.fillMaxHeight()) {

        CalculatorDisplay(
            value = viewModel.userInput,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = viewModel.output,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            textAlign = TextAlign.End,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

            SymbolButton(
                iconId = R.drawable.outline_calculate,
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f),
                onClick = {
                    //currentUI = "Landscape"
                }
            )

            Spacer(Modifier.weight(2f))

            SymbolButton(
                iconId = R.drawable.outline_backspace,
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f),
                onClick = {
                    viewModel.onBackspaceClicked()
                }
            )

        }

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(5.dp)
        )

        rows.forEach { row ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                row.forEach { button ->
                    RoundButton(
                        text = button.text,
                        modifier = Modifier
                            .weight(button.weight)
                            .clickable { button.onClick() },
                        fontSize = 20.sp
                    )

                }
            }

        }
    }
}


/*@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_TELEVISION,
    widthDp = 1920,
    heightDp = 1080
)
@Composable
fun PreviewCalculatorApp3() {
    LandscapeUI(openCamera = {})
}*/