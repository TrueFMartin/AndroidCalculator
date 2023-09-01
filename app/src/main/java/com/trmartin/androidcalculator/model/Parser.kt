package com.trmartin.androidcalculator.model

import android.os.Debug
import android.util.Log

class Parser(var input: String) {
    fun compute(): Double? {
        val splitNumbers: List<String> = input.split("+", "-", "*", "/")
        val operators = ArrayList<Char>()
        for (c in input){
            if(charArrayOf('+', '-', '*', '/').any{ it == c }) {
                operators.add(c)
            }
        }
        val trimmedNumbers = ArrayList<Double>()
        for (s in splitNumbers){
            try {
                trimmedNumbers.add(s.trim().toDouble())
            } catch (e: NumberFormatException) {
                Log.d("Parser","Invalid number format from input: ${e.message}")
                return null
            } catch (e: Exception){
                Log.d("Parser", "Other error: ${e.message}")
            }
        }
        return  0.0
    }

    fun sort(numList: ArrayList<Double>, operatorList: ArrayList<String>){
        for (i in 0..numList.size) {

        }
    }

}