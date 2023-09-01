package com.trmartin.androidcalculator.model

class InvalidInput() {
    companion object {
        const val MULTI_OPERATORS = "([-+*\\/]\\s*[-+*\\/])" // **, --, - -, + +, * *, / /
        const val MULTI_DECIMAL = "(\\d*\\.\\d*\\.\\d*)" // 3.4.5, .., ..3, 2..
        const val LONE_DECIMAL = "((?<!\\d)\\.(?!\\d))"// 3 - . + 2
        private val regex = Regex("${MULTI_OPERATORS}|${MULTI_DECIMAL}|${LONE_DECIMAL}")
        fun isValid(input: String): Boolean {
            return regex.containsMatchIn(input)
        }
    }
}