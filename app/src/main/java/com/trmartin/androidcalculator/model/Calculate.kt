package com.trmartin.androidcalculator.model

object Calculate {
    /**
     * Custom removal of a single element by slicing around an index.
     *
     * @param T ArrayList data type
     * @param l ArrayList that is sliced from
     * @param i index to slice around
     * @return the concatenation of slice 0..<i and i+1..l.size
     */
    private fun <T> customRemove(l: ArrayList<T>, i: Int): ArrayList<T>{
        val slice1 = l.slice(0..<i)
        val slice2 = l.slice(i+1..<l.size)
        return (slice1 + slice2) as ArrayList<T>
    }

    /**
     * Based on the operation passed in, perform the operation on two arguments and return the result.
     *
     * @param op '*', '/', '+', or '-'
     * @param lhs left hand side argument
     * @param rhs right hand side argument
     * @return result of lhs OP rhs
     */
    private fun operate(op: Char, lhs: Double, rhs: Double): Double{
        when(op) {
            '*' -> return lhs*rhs
            '/' -> return lhs/rhs
            '+' -> return lhs+rhs
            '-' -> return lhs-rhs
        }
        return Double.MAX_VALUE
    }

    /**
     * Loop through arguments in order, performing calculations, and returning the result
     *
     * @param numbers arguments, turned to double, in orig. order. Unmodified
     * @param operators (*, +, -, /), in the orig. order. Unmodified
     * @return result of final calculation. Double.MAX_VALUE if error
     */
    fun calculate(numbers: ArrayList<Double>, operators: ArrayList<Char>): Double {
        var numList = ArrayList<Double>(numbers)
        var operatorList = ArrayList<Char>(operators)

        // In order, operate on the list of operands '*', '/', '+', '-'
        for(operator in charArrayOf('*', '/', '+', '-')) {
            if(operatorList.size == 1) //Only one operand is left, and two arguments and loop still running
                return operate(operatorList[0], numList[0], numList[1])
            var size = operatorList.size
            var i = 0;
            // Use abnormal loop because 'size' is modified during the loop
            // TODO find alternative e.g. for (i in operatorList) that doesn't break
            while (i < size) {
                val c = operatorList[i]
                if (c == operator) {
                    val newNum = operate(c, numList[i], numList[i+1])
                    // Remove the 'rhs' of operand
                    numList = customRemove(numList, i)
                    // Replace the 'lhs' of operand with the new result
                    numList[i] = newNum
                    operatorList = customRemove(operatorList, i)
                }
                size = operatorList.size
                i++

            }
        }
        // Earlier 'if list size == 1 will not be caught if outer loop ends before checking last time
        if(operatorList.size == 1) //Only one operand is left, and two arguments
            return operate(operatorList[0], numList[0], numList[1])
        throw Exceptions.CalculationFailure("Error in Calculate. Current state: \n" +
                "Operators:\t$operatorList\n" +
                "Numbers:\t$numList")
    }
}