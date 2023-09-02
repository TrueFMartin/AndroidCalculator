package com.trmartin.androidcalculator.model

object Delegator {
    private val invalidInputChecker = InvalidInput.instance
    private val calculate = Calculate
    private val parsers = Parsers
    private var result = Double.MAX_VALUE
    fun calc(input: String): Double {
        if (input.isEmpty())
            throw Exceptions.EmptyInput("No input at all")
        if( invalidInputChecker.isInvalid(input))
            throw Exceptions.InvalidInput("Improper decimals or operator order on input: $input")
        val arguments = parsers.convertArguments(input)
            ?: throw Exceptions.InvalidInput("Invalid conversion to double from input: $input")
        val operators = parsers.convertOperators(input)
        if (operators.isEmpty())
            throw Exceptions.EmptyInput("No operators found from input: $input")
        result = calculate.calculate(arguments, operators)
        if (result == Double.MAX_VALUE)
            throw Exceptions.CalculationFailure("Calculation failed on input: $input")
        return result
    }
}