package com.trmartin.androidcalculator

interface Contract {

    interface View {
        fun setResult(output: String, isACalculation: Boolean)
        fun setError(errorMsg: String)
    }

    interface Presenter {
        fun updateFromView(op: CONTROLS, input: String)
    }

    interface Model {
        fun calc(input: String): Double
    }

    enum class CONTROLS {
        EQUALS, BACKSPACE, NEGATE
    }
}