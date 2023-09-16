package com.trmartin.androidcalculator

import com.trmartin.androidcalculator.model.Delegator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GradersUnitTests {
    @Test
    fun gradersMultipleTest() {
        val model: Contract.Model = Delegator
        val operations = mapOf(
            "9 + 4" to 13.0,
            "8 - 3" to 5.0,
            "2 * 8" to 16.0,
            "25 / 5" to 5.0,
            "-3.5 + 4" to 0.5,
            "25 - 27.2" to -2.2,
            "66666 * 99999" to 6.666533334E9,
            "2 / 10000000" to 2E-7,
            "24 + 31 + 7 + 8.5" to 70.5
        )
        for((actual, expected) in operations.entries) {
            assertEquals(model.calc(actual), expected, 0.01)
        }

    }

}