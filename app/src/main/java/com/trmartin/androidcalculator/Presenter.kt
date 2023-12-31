package com.trmartin.androidcalculator

import com.trmartin.androidcalculator.model.Delegator
import com.trmartin.androidcalculator.model.Exceptions

/**
 * Presenter
 *
 * @property view
 * @constructor Create empty Presenter
 */
class Presenter(private val view: Contract.View): Contract.Presenter {
    private val model: Contract.Model
    private val numberRegex = Regex("[0-9]")
    init {
        model = Delegator
    }

    override fun updateFromView(op: Contract.CONTROLS, input: String) {
        when(op) {
            Contract.CONTROLS.EQUALS -> calc(input)
            Contract.CONTROLS.BACKSPACE -> backspace(input)
            Contract.CONTROLS.NEGATE -> negate(input)
        }
    }

    /**
     * Call model's 'calc' method. Respond from errors thrown by model
     * and set the result of the view's 'text view result'.
     *
     * @param input to pass to calc
     */
    private fun calc(input: String) {
        try {
            var modifiedInput = input
            if (input.contains('e')) {
                val splitInput = input.split(" ") as MutableList
                splitInput[0] = splitInput[0].toDouble().toString()
                val builder = StringBuilder()
                for ((index, s) in splitInput.withIndex()) {
                    builder.append(s)
                    if (index != splitInput.lastIndex || !s.contains(numberRegex)) {
                        builder.append(' ')
                    }
                }
                modifiedInput = builder.toString()
            }
            view.setResult(formatPrintout(model.calc(modifiedInput)), true)
        } catch (_: Exceptions.EmptyInput){
            view.setError("EMPTY")
        } catch (_: Exceptions.MultipleDecimalException){
            view.setError("MultipleDecimals")
        } catch (_: Exceptions.MultipleOperatorException){
            view.setError("MultipleOperators")
        } catch (_: Exceptions.LoneDecimalException) {
            view.setError("LoneDecimal")
        } catch (_: Exceptions.EndsInOperatorException) {
            view.setError("EndInOperator")
        } catch (_: Exceptions.StartsWithOperatorException) {
            view.setError("StartWithOperator")
        } catch (_: Exceptions.CalculationFailure) {
            view.setError("ERROR:UNKNOWN")
        }
    }

    /**
     * Format printout for Main Activity's 'text view result',
     * replacing 'Infinite' with 'Undefined' and converting whole
     * numbers into integer displays.
     *
     * @param result to be formatted
     * @return the formatted result
     */
    private fun formatPrintout(result: Double): String {
        if (result.isInfinite())
            return "Undefined"
        // If a whole number
        if (result.toInt().toDouble() == result)
            return result.toInt().toString()
        val strResult = result.toString()
        if (strResult.contains("e", true)) {
           return removeZeros(String.format("%.6e", result))
        } else {
            return removeZeros(String.format("%f", result))
        }
    }

    /**
     * Remove trailing zeros from decimals. Works on 7.3000e9
     *
     * @param input to remove 0's from
     * @return the same input with the removed trailing zeros
     */
    private fun removeZeros(input: String): String{
        if (input.contains('e')) {
            val decimalIndex = input.indexOf('.')
            val eIndex = input.indexOf('e')
            val stringBuilder = StringBuilder()
            var firstExtraZero = eIndex
            stringBuilder.append(input.substringBefore('.'))
            for (i in eIndex - 1 downTo decimalIndex) {
                if (input[i] == '0') firstExtraZero = i else break
            }
            if (firstExtraZero == decimalIndex + 1) {
                stringBuilder.append(input.substring(eIndex..input.lastIndex))
            } else {
                stringBuilder.append(input.substring(decimalIndex..<firstExtraZero))
                stringBuilder.append(input.substring(eIndex..input.lastIndex))
            }
            return stringBuilder.toString()
        } else {
            var firstExtraZero = input.lastIndex
            for ( i in input.lastIndex downTo input.indexOf('.') + 1) {
                if (input[i] == '0') firstExtraZero = i else break
            }
            if (firstExtraZero == input.lastIndex) {
                return input
            } else {
                return input.substring(0..<firstExtraZero)
            }
        }
    }

    private fun backspace(input: String){
        // If last entry (far right) is an operator
        if (input.endsWith(" ")) {
            view.setResult(input.dropLast(3), false)
        // Else it's not an operator, just remove last char
        } else {
            view.setResult(input.dropLast(1), false)
        }
    }


    /**
     * Perform logic to decdie where the '-' (negative) should be placed,
     * or if the current string should be made positive (fun getLastArgumentNegated)
     *
     * @param input to negate
     */
    private fun negate(input: String) {
        // Add '-' at front
        if (input.isEmpty()) {
            view.setResult("-", false)
        // Add to far right, after an operator
        } else if(input.endsWith(" ")) {
            view.setResult(input.plus("-"), false)
        } else {
            val(index, lastArg) = getLastArgumentNegated(input)
            view.setResult(input.replaceRange(index..input.lastIndex, lastArg), false)
        }
    }

    /**
     * Removes the last argument (far right) from the string,
     * negates its current value (-1 -> 1, 4 -> -4), and returns
     * a pair of the index to place the last argument, and the
     * negated argument.
     *
     * @param input to negate
     * @return pair of <index where string should be place, modified string>
     */
    private fun getLastArgumentNegated(input: String): Pair<Int, String> {
        val split = input.split(" ")
        val lastArg = split[split.lastIndex]
        val indexOfLast = input.lastIndexOf(lastArg)
        if(lastArg.contains('-')) {
            return Pair(indexOfLast, lastArg.replace("-", ""))
        } else {
            return Pair(indexOfLast, "-".plus(lastArg))
        }
    }
}