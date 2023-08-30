package com.trmartin.androidcalculator.model

data class Argument(var doubleVal: Double, var strVal: String, var isInt: Boolean){
    constructor(strVal: String): this(strVal.toDouble(), strVal, strVal.contains("."))
    constructor(doubleVal: Double, isInt: Boolean): this(doubleVal, "RESULT", isInt)

}
