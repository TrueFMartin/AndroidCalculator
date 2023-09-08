package com.trmartin.androidcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlin.properties.Delegates

/**
 * Main activity
 *
 * @constructor Create Main activity
 */
class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter
    private lateinit var tvResult: TextView
    private lateinit var tvTitle: TextView
    // Was the previous command (and the current text of tvResult), a calculation?
    // If so, use different logic to clear or append tvResult
    private var isACalculation = false
    // If an error, clear the 'is error' screen after new entries
    private var isError = false
    // Colors to set the background of the 'error display text view'
    private var nonErrorColor by Delegates.notNull<Int>()
    private var errorColor by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Init the late init fields. Presenter handles business display logic of MainActivity
        // and mediates 'model'
        presenter = Presenter(this)
        tvResult = findViewById(R.id.tvResult)
        tvTitle = findViewById(R.id.tvTitle)

        nonErrorColor = resources.getColor(R.color.non_error_color, this.theme)
        errorColor = resources.getColor(R.color.error_color, this.theme)

        // Add listener for all 'number' buttons, appending the number to the tvResult
        findViewById<Button>(R.id.button0).setOnClickListener{addResults("0")}
        findViewById<Button>(R.id.button1).setOnClickListener{addResults("1")}
        findViewById<Button>(R.id.button2).setOnClickListener{addResults("2")}
        findViewById<Button>(R.id.button3).setOnClickListener{addResults("3")}
        findViewById<Button>(R.id.button4).setOnClickListener{addResults("4")}
        findViewById<Button>(R.id.button5).setOnClickListener{addResults("5")}
        findViewById<Button>(R.id.button6).setOnClickListener{addResults("6")}
        findViewById<Button>(R.id.button7).setOnClickListener{addResults("7")}
        findViewById<Button>(R.id.button8).setOnClickListener{addResults("8")}
        findViewById<Button>(R.id.button9).setOnClickListener{addResults("9")}

        // Add listener for all operator and decimal buttons, appending to end of tvResult
        findViewById<Button>(R.id.buttonAdd).setOnClickListener{addResults(" + ")}
        findViewById<Button>(R.id.buttonMinus).setOnClickListener{addResults(" - ")}
        findViewById<Button>(R.id.buttonProduct).setOnClickListener{addResults(" * ")}
        findViewById<Button>(R.id.buttonDivide).setOnClickListener{addResults(" / ")}
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener{addResults(".")}

        // Add listener for the remainder of buttons, that require more logic. Most handled by presenter
        findViewById<Button>(R.id.buttonNegate).setOnClickListener{
            presenter.updateFromView(Contract.CONTROLS.NEGATE, tvResult.text as String)
        }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener{
            presenter.updateFromView(Contract.CONTROLS.EQUALS, tvResult.text as String)
        }
        findViewById<Button>(R.id.buttonClear).setOnClickListener{
            val e = ""
            tvResult.text = e
            clearError()
        }
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener{
            // If previous result was a calculation, clear the result
            if (isACalculation) {
                tvResult.text = ""
            // Else let presenter handle how backspace should act
            } else {
                presenter.updateFromView(Contract.CONTROLS.BACKSPACE, tvResult.text as String)
            }
        }
    }


    private fun addResults(addition: String) {
        if (!isACalculation) { //Do not overwrite current 'tvResult', just append the addition
            val newResult = tvResult.text as String + addition
            tvResult.text = newResult
        // If a calculation was just performed, and user entered a number, overwrite it
        } else if (addition.isDigitsOnly() || addition == "." || tvResult.text == "Undefined"){
            tvResult.text = addition
        // If calculation was performed, and we don't want to overwrite the results,
        // we instead add on top of them
        } else { //Overwrite the previous final 'result'
            val newResult = tvResult.text as String + addition
            tvResult.text = newResult
        }
        isACalculation = false
        clearError()
    }

    override fun setResult(output: String, isACalculation: Boolean) {
        if (isError) {
            clearError()
        }
        this.isACalculation = isACalculation
        isError = false
        tvResult.text = output
    }

    override fun setError(errorMsg: String) {
        isError = true
        isACalculation = false
        tvTitle.text = errorMsg
        tvTitle.setBackgroundColor(errorColor)
    }

    private fun clearError() {
        val nonErrorText = "Calculate:"
        tvTitle.text = nonErrorText
        tvTitle.setBackgroundColor(nonErrorColor)
    }
}