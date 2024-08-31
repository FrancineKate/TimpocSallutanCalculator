package com.example.timpoc1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operator: String= ""
    private var firstValue: String= ""
    private var secondValue: String = ""
    private var isOperatorSelected: Boolean = false
    private var isResultDisplayed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.resultTextView)

        val buttons = listOf(
            findViewById<Button>(R.id.AC),
            findViewById<Button>(R.id.plusminus),
            findViewById<Button>(R.id.percent),
            findViewById<Button>(R.id.divide),
            findViewById<Button>(R.id.seven),
            findViewById<Button>(R.id.eight),
            findViewById<Button>(R.id.nine),
            findViewById<Button>(R.id.multiply),
            findViewById<Button>(R.id.four),
            findViewById<Button>(R.id.five),
            findViewById<Button>(R.id.six),
            findViewById<Button>(R.id.minus),
            findViewById<Button>(R.id.one),
            findViewById<Button>(R.id.two),
            findViewById<Button>(R.id.three),
            findViewById<Button>(R.id.add),
            findViewById<Button>(R.id.zero),
            findViewById<Button>(R.id.decimal),
            findViewById<Button>(R.id.equals),

            )

            buttons.forEach { button ->
               button.setOnClickListener { onButtonClick(button) }
            }
        }

    private fun onButtonClick(button: Button) {
        val buttonText = button.text.toString()

        when (buttonText) {
            "AC" -> clear()
            "±" -> toggleSign()
            "%" -> applyPercentage()
            "÷", "x", "-", "+" -> handleOperator(buttonText)
            "=" -> calculateResult()
            "." -> appendDecimal()
            else -> appendNumber(buttonText)
        }
    }

    private fun clear() {
        display.text = "0"
        operator = ""
        firstValue = ""
        secondValue = ""
        isOperatorSelected = false
        isResultDisplayed = false
    }

    private fun toggleSign() {
        val currentValue = display.text.toString()
        if (currentValue != "0") {
            display.text = if (currentValue.startsWith("-")) {
                currentValue.substring(1)
            } else {
                "-$currentValue"
            }
        }
    }

    private fun applyPercentage() {
        val currentValue = display.text.toString().toDouble() / 100
        display.text = currentValue.toString()
    }

    private fun handleOperator(op: String) {
        if (isResultDisplayed) {
            firstValue = display.text.toString()
            operator = op
            isOperatorSelected = true
            isResultDisplayed = false
        } else if (isOperatorSelected) {
            secondValue = display.text.toString()
            calculateResult()
            operator = op
            isOperatorSelected = true
            firstValue = display.text.toString()
        } else {
            firstValue = display.text.toString()
            operator = op
            isOperatorSelected = true
        }
    }

    private fun calculateResult() {
        if (firstValue.isNotEmpty() && operator.isNotEmpty()) {
            secondValue = display.text.toString()

            val result = when (operator) {
                "+" -> firstValue.toDouble() + secondValue.toDouble()
                "-" -> firstValue.toDouble() - secondValue.toDouble()
                "x" -> firstValue.toDouble() * secondValue.toDouble()
                "÷" -> firstValue.toDouble() / secondValue.toDouble()
                else -> 0.0
            }

            // Format result: remove decimal point if there's no decimal part
            display.text = if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }

            operator = ""
            isOperatorSelected = false
            isResultDisplayed = true
        }
    }

    private fun appendDecimal() {
        if (!display.text.contains(".")) {
            display.append(".")
        }
    }

    private fun appendNumber(number: String) {
        if (display.text == "0" || isOperatorSelected || isResultDisplayed) {
            display.text = number
            isOperatorSelected = false
            isResultDisplayed = false
        } else {
            display.append(number)
        }
    }
}