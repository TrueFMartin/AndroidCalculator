package com.trmartin.androidcalculator.model

import android.util.Log

/**
 * Parser used to split arguments from operators, order them, and initialize calculations.
 *
 * @property input String that has already been checked against 'InvalidInput' and is now to be parsed
 * @constructor Create empty Parser
 */
object Parsers {

    /**
     * Test if a character is one of: '+', '-', '*', '/'
     *
     * @return true if this is an operand
     */
    private fun Char.isOperand(): Boolean {
        return charArrayOf('+', '-', '*', '/').any { it == this }
    }


    private val negativeRegex = Regex("(-\\d+)|(-\\.+)")

    /**
     * Parse input.
     * @param s string to parse and convert
     * @return ArrayList<Char> of each operand in the same order as 's'
     */
    fun convertOperators(s: String): ArrayList<Char>{
        // Remove instances of negative numbers, '-3', they throw off Char.isOperand()
        val strWithoutNegatives = s.replace(negativeRegex, "")
        val operatorString = strWithoutNegatives.filter {it.isOperand()}.toCharArray()
        if (operatorString.isEmpty())
            return arrayListOf()
        if (operatorString.size == 1){
            // Can not cast a string of size 1 to an ArrayList like normal operation below
            // E.g. '3 + 3' will try and cast '+' .toList() as ArrayList<Char> and fail
            return arrayListOf(operatorString[0])
        }
        return operatorString.toList() as ArrayList<Char>
    }

    /**
     * Parse string into only the arguments
     * @param s string to parse and convert
     * @return ArrayList<Double> of each argument in the same order as 's'
     */
    fun convertArguments(s: String): ArrayList<Double>? {
        val splitNumbers: List<String> = s.split(" + ", " - ", " * ", " / ")


        val trimmedNumbers = ArrayList<Double>()
        for (numString in splitNumbers){
            try {
                trimmedNumbers.add(numString.trim().toDouble())
            } catch (e: NumberFormatException) {
                Log.d("Parser","Invalid number format from input: ${e.message}")
                return null
            } catch (e: Exception){
                Log.d("Parser", "Other error: ${e.message}")
            }
        }
        return trimmedNumbers
    }
}
