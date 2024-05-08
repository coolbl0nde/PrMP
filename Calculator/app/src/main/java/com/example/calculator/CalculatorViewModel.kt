package com.example.calculator

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.data.CalculatorOperation
import com.example.calculator.services.OperationStorageService
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewModel : ViewModel(){

    var userInput by mutableStateOf("")

    var output by mutableStateOf("")
        private set

    private var mathExpression = ""

    private var hasOpeningBracket = false

    private val operators = listOf(
        "+", "-", "*", "/", "^", "%", ".",
    )

    private var prevSymbol = ""

    fun onSymbolClicked(originalSymbol: String){

        var symbol = originalSymbol

        val regex = Regex("\\(\\-\\d$")

        if (this.operators.contains(symbol) && (this.operators.contains(prevSymbol) || prevSymbol.isEmpty())) {
            return
        }

        if (symbol == "%") {
            mathExpression += "/100"
        } else if (symbol == "()") {
            symbol = if (hasOpeningBracket) ")" else "("
            hasOpeningBracket = !hasOpeningBracket
            mathExpression += symbol
        } else if (userInput.isNotEmpty() && userInput.last() == '%') {
            mathExpression += "*$symbol"
            userInput += "*"
        } else if (regex.containsMatchIn(userInput) && symbol == "+/-"){
            mathExpression = mathExpression.dropLast(3) + mathExpression.last()
            userInput = userInput.dropLast(3) + userInput.last()
            symbol = ""
        } else if (symbol == "+/-"){
            mathExpression = mathExpression.dropLast(1) + "(-" + mathExpression.last()
            userInput = userInput.dropLast(1) + "(-" + userInput.last()
            hasOpeningBracket = !hasOpeningBracket
            symbol = ""
        } else if (symbol == "sin(" || symbol == "cos(" || symbol == "ln("){
            mathExpression += symbol
            hasOpeningBracket = !hasOpeningBracket
        } else if (symbol == "sqrt("){
            hasOpeningBracket = !hasOpeningBracket
            mathExpression += symbol
            symbol = "√("
        } else if (symbol == "log(10,"){
            hasOpeningBracket = !hasOpeningBracket
            mathExpression += symbol
            symbol = "log("
        } else {
            mathExpression += symbol
        }

        userInput += symbol
        prevSymbol = symbol

        outputResult()
    }

    fun onClearClicked(){
        userInput = ""
        output = ""
        mathExpression = ""
        prevSymbol = ""
        hasOpeningBracket = false
    }


    fun onEqualClicked(context: Context){
        val result = evaluateMathExpression().toString()
        val operationStorageService = OperationStorageService()

        if (result != "NaN"){

            if (result.endsWith(".0")){
                result.dropLast(2)
            }

            operationStorageService.addOperation(
                CalculatorOperation(input = userInput, result = result),
                onSuccess = {
                    Log.d("CalculatorViewModel", "Operation added successfully!")
                            },
                onFailure = { exception -> exception.printStackTrace() }
            )

            userInput = result
        }else{
            Toast.makeText(context, "Неверный формат", Toast.LENGTH_SHORT).show()
        }
    }


    fun onBackspaceClicked(){
        if(userInput != ""){
            when (userInput.last()) {
                '(' -> hasOpeningBracket = false
                ')' -> hasOpeningBracket = true
            }

            mathExpression = mathExpression.dropLast(1)
            userInput = userInput.dropLast(1)
        } else{
            prevSymbol = ""
        }

        outputResult()
    }

    private fun evaluateMathExpression(): Double {
        val e = Expression(mathExpression)
        return e.calculate()
    }

    private fun outputResult(){
        val result = evaluateMathExpression().toString()

        if (result != "NaN"){
            if (result.endsWith(".0")){
                result.dropLast(2)
            }
            output = result
        }else{
            output = ""
        }
    }

}