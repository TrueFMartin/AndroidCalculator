package com.trmartin.androidcalculator.model

enum class Operators {
    PLUS{
        override fun apply(lhs: Argument, rhs: Argument): Argument {
            val isInt = lhs.isInt && rhs.isInt
            return Argument(lhs.doubleVal + rhs.doubleVal, isInt)
        }
    },
    MINUS{
        override fun apply(lhs: Argument, rhs: Argument): Argument {
            val isInt = lhs.isInt && rhs.isInt
            return Argument(lhs.doubleVal - rhs.doubleVal, isInt)
        }
    },
    MULTIPLY{
        override fun apply(lhs: Argument, rhs: Argument): Argument {
            val isInt = lhs.isInt && rhs.isInt
            return Argument(lhs.doubleVal * rhs.doubleVal, isInt)
        }
    },
    DIVIDE{
        override fun apply(lhs: Argument, rhs: Argument): Argument {
            val isInt = lhs.isInt && rhs.isInt && (lhs.doubleVal % rhs.doubleVal).toInt() == 0
            //FIXME Make sure that the above casting to int works
            return Argument(lhs.doubleVal / rhs.doubleVal, isInt)
        }
    };
    abstract fun apply(lhs: Argument, rhs: Argument): Argument
}