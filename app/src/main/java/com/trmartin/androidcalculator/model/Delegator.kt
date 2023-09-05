package com.trmartin.androidcalculator.model

import com.trmartin.androidcalculator.Contract

object Delegator: Contract.Model {
    private val invalidInputChecker = InvalidInput.instance
    private val calculate = Calculate
    private val parsers = Parsers
    private var result = Double.MAX_VALUE
    override fun calc(input: String): Double {
        if (input.isEmpty())
            throw Exceptions.EmptyInput("No input at all")
        // Throw exception depending on error(decimals or operators)
        if( invalidInputChecker.isInvalid(input))
            throw Exceptions.CalculationFailure("Should Never Happen!")
        val arguments = parsers.convertArguments(input)
            ?: throw Exceptions.CalculationFailure("Invalid conversion to double from input: $input")
        val operators = parsers.convertOperators(input)
        if (operators.isEmpty())
            throw Exceptions.EmptyInput("No operators found from input: $input")
        result = calculate.calculate(arguments, operators)
        if (result == Double.MAX_VALUE)
            throw Exceptions.CalculationFailure("Calculation failed on input: $input")
        return result
    }
}