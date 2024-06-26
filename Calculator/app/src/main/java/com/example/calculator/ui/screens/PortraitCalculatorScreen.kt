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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calculator.data.ButtonConfig
import com.example.calculator.CalculatorViewModel
import com.example.calculator.R
import com.example.calculator.ui.components.CalculatorDisplay
import com.example.calculator.ui.components.RoundButton
import com.example.calculator.ui.components.SymbolButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calculator.SetupViewModel
import com.example.calculator.ThemeViewModel
import com.example.calculator.ui.components.ChangeKeyDialog
import com.example.calculator.ui.components.TopAppBar


@Composable
fun CalculatorApp(viewModel: CalculatorViewModel,
                  themeViewModel: ThemeViewModel = ThemeViewModel(),
                  navController: NavController,
                  setupViewModel: SetupViewModel
){

    val context = LocalContext.current

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
        ButtonConfig(text = "=", onClick = { viewModel.onEqualClicked(
            context) }),
    )

    val rows = buttons.chunked(4)

    var showDialog by remember { mutableStateOf(false) }

    var currentUI by remember {
        mutableStateOf("")
    }


    Column (
        modifier = Modifier.fillMaxHeight()
    ) {

        TopAppBar(themeViewModel)

        CalculatorDisplay(
            value = viewModel.userInput,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .weight(1.5f)
        )

        Text(
            text = viewModel.output,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 10.dp)
                .weight(0.5f),
            textAlign = TextAlign.End,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
        ) {
            SymbolButton(
                iconId = R.drawable.outline_calculate,
                modifier = Modifier
                    .weight(1f),
                onClick = { navController.navigate("cameraScreen") }
            )

            SymbolButton(
                iconId = R.drawable.baseline_access_time_24,
                modifier = Modifier
                    .weight(1f),
                onClick = { navController.navigate("operationScreen") }
            )

            SymbolButton(
                iconId = R.drawable.outline_build_circle_24,
                modifier = Modifier
                    .weight(1f),
                onClick = { showDialog = true }
            )

            Spacer(Modifier.weight(2f))

            SymbolButton(
                iconId = R.drawable.outline_backspace,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    viewModel.onBackspaceClicked()
                }
            )

            ChangeKeyDialog(
                showDialog = showDialog,
                setShowDialog = {showDialog = it},
                onConfirm = {
                    setupViewModel.setPassKey(it)
                },
                deleteKey = {
                    if (it){
                        setupViewModel.resetPassKey()
                        navController.navigate("setupScreen")
                    }
                }
            )
        }

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
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