package com.trmartin.androidcalculator.model

class Operation(
    var lhs: Argument?,
    var rhs: Argument?,
    var operators: Operators,
    var nextDirection: DIRECTION) {

    var next: Operation? = null

    public enum class DIRECTION{
        LEFT, RIGHT
    }
    fun calc(): Argument {
        val result = operators.apply(lhs!!, rhs!!)
        if (next == null) {
            return result
        } else {
            if(nextDirection == DIRECTION.LEFT){
                next!!.rhs = result
            } else {
                next!!.lhs = result
            }
            return next!!.calc()
        }

    }

}