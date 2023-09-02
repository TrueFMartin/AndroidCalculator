package com.trmartin.androidcalculator.model

/**
 * Singleton regex matching class to flag invalid input.
 *
 * @constructor private: Use 'InvalidInput.instance'
 */
class InvalidInput private constructor() {
    companion object { // Provide singleton property of class
        val instance: InvalidInput by lazy {
            InvalidInput()
        }
            private const val MULTI_OPERATORS = "(\\s+[-+*\\/]\\s+[-+*\\/]\\s+)" // * *, - -, - -, + +, * *, / /, - *
            private const val MULTI_DECIMAL = "(\\d*\\.\\d*\\.\\d*)" // 3.4.5, .., ..3, 2..
            private const val LONE_DECIMAL = "((?<!\\d)\\.(?!\\d))"// 3 - . + 2
            private val regex = Regex("${MULTI_OPERATORS}|${MULTI_DECIMAL}|${LONE_DECIMAL}")
        }

    /**
     * Is input valid or invalid?
     *
     * @param input String to run the regex against.
     * @return Boolean 'true' if none of the invalid operation expressions are found
     */
    fun isInvalid(input: String): Boolean {
            return regex.containsMatchIn(input)
        }
}