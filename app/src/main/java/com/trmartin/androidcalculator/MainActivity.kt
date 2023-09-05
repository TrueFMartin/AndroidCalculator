package com.trmartin.androidcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), Contract.View, View.OnClickListener {

    private lateinit var presenter: Contract.Presenter
    private lateinit var tvResult: TextView
    private lateinit var tvTitle: TextView

    private var isACalculation = false
    private var isError = false
    private var nonErrorColor by Delegates.notNull<Int>()
    private var errorColor by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = Presenter(this)
        tvResult = findViewById(R.id.tvResult)
        tvTitle = findViewById(R.id.tvTitle)

        nonErrorColor = resources.getColor(R.color.non_error_color, this.theme)
        errorColor = resources.getColor(R.color.error_color, this.theme)
        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)

        findViewById<Button>(R.id.buttonAdd).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMinus).setOnClickListener(this)
        findViewById<Button>(R.id.buttonProduct).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDivide).setOnClickListener(this)

        findViewById<Button>(R.id.buttonEquals).setOnClickListener(this)
        findViewById<Button>(R.id.buttonClear).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener(this)
        findViewById<Button>(R.id.buttonNegate).setOnClickListener(this)




    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button0 -> addResults("0")
            R.id.button1 -> addResults("1")
            R.id.button2 -> addResults("2")
            R.id.button3 -> addResults("3")
            R.id.button4 -> addResults("4")
            R.id.button5 -> addResults("5")
            R.id.button6 -> addResults("6")
            R.id.button7 -> addResults("7")
            R.id.button8 -> addResults("8")
            R.id.button9 -> addResults("9")

            R.id.buttonDecimal -> addResults(".")

            R.id.buttonAdd -> addResults(" + ")
            R.id.buttonMinus -> addResults(" - ")
            R.id.buttonProduct -> addResults(" * ")
            R.id.buttonDivide -> addResults(" / ")

            R.id.buttonClear ->  {
                val e = ""
                tvResult.text = e
                clearError()
            }

            R.id.buttonNegate -> presenter.updateFromView(
                Contract.CONTROLS.NEGATE, tvResult.text as String)


            R.id.buttonBackspace -> presenter.updateFromView(
                Contract.CONTROLS.BACKSPACE, tvResult.text as String)

            R.id.buttonEquals -> presenter.updateFromView(
                Contract.CONTROLS.EQUALS, tvResult.text as String)

        }
    }

    private fun addResults(addition: String) {
        if (!isACalculation) { //Do not overwrite current 'tvResult', just append the addition
            val newResult = tvResult.text as String + addition
            tvResult.text = newResult
        } else { //Overwrite the previous final 'result'
            tvResult.text = addition
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