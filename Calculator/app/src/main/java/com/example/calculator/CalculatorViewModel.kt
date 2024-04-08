package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewModel : ViewModel(){

    var userInput by mutableStateOf("")
        private set

    var output by mutableStateOf("")
        private set

    private var mathExpression by mutableStateOf("")

    private var hasOpeningBracket by mutableStateOf(false)

    var state = CalculatorModel()
        private set

    fun onSymbolClicked(originalSymbol: String){

        var symbol = originalSymbol

        val regex = Regex("\\(\\-\\d$")

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

        outputResult()
    }

    fun onClearClicked(){
        userInput = ""
        output = ""
        mathExpression = ""
        hasOpeningBracket = false
    }

    fun onEqualClicked(){
        val result = evaluateMathExpression().toString()

        userInput = mathExpression
        //userInput = result
    }


    fun onBackspaceClicked(){
        if(userInput != ""){
            when (userInput.last()) {
                '(' -> hasOpeningBracket = false
                ')' -> hasOpeningBracket = true
            }

            mathExpression = mathExpression.dropLast(1)
            userInput = userInput.dropLast(1)
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
            output = result
        }else{
            output = ""
        }
    }


}