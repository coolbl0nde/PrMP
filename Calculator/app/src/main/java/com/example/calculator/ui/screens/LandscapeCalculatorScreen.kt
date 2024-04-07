package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ButtonConfig
import com.example.calculator.CalculatorViewModel
import com.example.calculator.R
import com.example.calculator.ui.components.CalculatorDisplay
import com.example.calculator.ui.components.RoundButton
import com.example.calculator.ui.components.SymbolButton

@Composable
fun LandscapeUI(viewModel: CalculatorViewModel = CalculatorViewModel()) {

    val buttons = listOf(
        ButtonConfig(text = "sin", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "cos", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "C", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "()", onClick = { /* обработка нажатия */ }),
        ButtonConfig(text = "%", onClick = { viewModel.onSymbolClicked("%") }),
        ButtonConfig(text = "/", onClick = { viewModel.onSymbolClicked("/") }),
        ButtonConfig(text = "7", onClick = { viewModel.onSymbolClicked("7") }),
        ButtonConfig(text = "8", onClick = { viewModel.onSymbolClicked("8") }),
        ButtonConfig(text = "9", onClick = { viewModel.onSymbolClicked("9") }),
        ButtonConfig(text = "*", onClick = { viewModel.onSymbolClicked("*") }),
        ButtonConfig(text = "C", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "C", onClick = { viewModel.onClearClicked() }),
        ButtonConfig(text = "4", onClick = { viewModel.onSymbolClicked("4") }),
        ButtonConfig(text = "5", onClick = { viewModel.onSymbolClicked("5") }),
        ButtonConfig(text = "6", onClick = { viewModel.onSymbolClicked("6") }),
        ButtonConfig(text = "-", onClick = { viewModel.onSymbolClicked("-") }),
        ButtonConfig(text = "1", onClick = { viewModel.onSymbolClicked("1") }),
        ButtonConfig(text = "2", onClick = { viewModel.onSymbolClicked("2") }),
        ButtonConfig(text = "3", onClick = { viewModel.onSymbolClicked("3") }),
        ButtonConfig(text = "+", onClick = { viewModel.onSymbolClicked("+") }),
        ButtonConfig(text = "+/-", onClick = { /* обработка нажатия */ }),
        ButtonConfig(text = "0", onClick = { viewModel.onSymbolClicked("0") }),
        ButtonConfig(text = ",", onClick = { viewModel.onSymbolClicked(".") }),
        ButtonConfig(text = "=", onClick = { viewModel.onEqualClicked() }),
    )

    Column(modifier = Modifier.fillMaxHeight()) {

        CalculatorDisplay(
            value = "123",
            modifier = Modifier.weight(2f)
        )

        Text(text = "хуй")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

            SymbolButton(
                iconId = R.drawable.outline_calculate,
                modifier = Modifier
                    .size(30.dp),
                onClick = {
                    //currentUI = "Landscape"
                }
            )

            Spacer(Modifier.weight(2f))

            SymbolButton(
                iconId = R.drawable.outline_backspace,
                modifier = Modifier
                    .size(30.dp),
                onClick = {
                    //viewModel.onBackspaceClicked()
                }
            )

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.padding(5.dp)
            )
        }

        /*Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            RoundButton(
                text = "sin",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "cos",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "C",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "()",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "%",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "/",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            RoundButton(
                text = "-2",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "1/x",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "7",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "8",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(8)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "9",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(9)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "*",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            RoundButton(
                text = "ln",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "log",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "4",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(4)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "5",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(5)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "6",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(6)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "-",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            RoundButton(
                text = "x2",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "xy",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "1",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(1)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "2",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(2)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "3",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(3)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "+",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            RoundButton(
                text = "|x|",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "pi",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "+/-",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "0",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(0)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = ",",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )

            RoundButton(
                text = "=",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.onNumberClicked(7)
                    },
                fontSize = 15.sp
            )
        }*/
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_TELEVISION,
    widthDp = 1920,
    heightDp = 1080
)
@Composable
fun PreviewCalculatorApp3() {
    LandscapeUI()
}