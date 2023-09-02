package com.trmartin.androidcalculator.model

import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun parse() {
        fun <T> customRemove(l: ArrayList<T>, i: Int): ArrayList<T>{
            val slice1 = l.slice(0..<i)
            val slice2 = l.slice(i+1..<l.size)
            return (slice1 + slice2) as ArrayList<T>
        }
        fun operate(op: Char, lhs: Double, rhs: Double): Double{
            when(op) {
                '*' -> return lhs*rhs
                '/' -> return lhs/rhs
                '+' -> return lhs+rhs
                '-' -> return lhs-rhs
            }
            return Double.MAX_VALUE
        }
        fun sort(doubleList: ArrayList<Double>, stringList: ArrayList<Char>): Double {
            var numList = ArrayList<Double>(doubleList)
            var operatorList = ArrayList<Char>(stringList)

            for(operator in charArrayOf('*', '/', '+', '-')) {
                if(operatorList.size == 1)
                    return operate(operatorList[0], numList[0], numList[1])
                var size = operatorList.size
                var i = 0;
                while (i < size) {
                    val c = operatorList[i]
                    if (c == operator) {
                        println("Before Remove: char = $operator, i=$i")
                        val newNum = operate(c, numList[i], numList[i+1])
                        numList = customRemove(numList, i)
                        numList[i] = newNum
                        operatorList = customRemove(operatorList, i)
                        println(operatorList)
                        println(numList)
                    }
                    size = operatorList.size
                    i++

                }
            }
            return Double.MAX_VALUE
        }


        val a1 = arrayListOf<Double>(4.0, 2.0,4.0,6.0,3.0,7.0)
        val a2 = arrayListOf<Char>('*', '/', '+', '*', '-')
        println(sort(a1, a2))
        assert(1==1)

    }
}