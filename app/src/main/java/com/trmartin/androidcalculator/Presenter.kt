package com.trmartin.androidcalculator

import com.trmartin.androidcalculator.model.Delegator
import com.trmartin.androidcalculator.model.Exceptions

class Presenter(val view: Contract.View): Contract.Presenter {
    private val mModel: Contract.Model
    init {
        mModel = Delegator
    }
    // FIXME is it better to have two methods named by the interface
    override fun updateFromView(op: Contract.CONTROLS, input: String) {
        when(op){
            Contract.CONTROLS.EQUALS -> calc(input)
            Contract.CONTROLS.BACKSPACE -> backspace(input)
            Contract.CONTROLS.NEGATE -> negate(input)
            // ... IF it was a more robust APP this method wouldn't be so useless

        }
    }

    private fun calc(input: String) {
        try {
            view.setResult(formatDisplay(mModel.calc(input)), true)
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

    private fun formatDisplay(result: Double): String {
        // If a whole number
        if (result.toInt().toDouble() == result)
            return result.toInt().toString()
        return result.toString()
    }

    private fun backspace(input: String){
        if (input.endsWith(" ")) {
            view.setResult(input.dropLast(3), false)
        } else {
            view.setResult(input.dropLast(1), false)
        }
    }

    private fun negate(input: String) {
        if (input.isEmpty()) {
            view.setResult("-", false)
        } else if(input.endsWith(" ")) {
            view.setResult(input.plus("-"), false)
        } else {
            val(index, lastArg) = getLastArgumentNegated(input)
            view.setResult(input.replaceRange(index..input.lastIndex, lastArg), false)
        }
    }

    private fun getLastArgumentNegated(input: String): Pair<Int, String>{
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