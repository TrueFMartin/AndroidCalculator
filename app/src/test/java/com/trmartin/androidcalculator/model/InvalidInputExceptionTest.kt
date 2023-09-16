package com.trmartin.androidcalculator.model

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class InvalidInputExceptionTest {
    @Test
    fun isInvalidTest() {
        val validInput1 = "2 * 4 - 7.3 + .3 - 3."
        val validInput2 = ".2 * .4 - 7.3 + .3 - 3."
        val validInput3 = "2.00000 * 41111.222 - 7.3 + .3 - 3."

        val validInputs = arrayListOf<String>(validInput1, validInput2, validInput3)

        val invalidInputChecker1 = InvalidInput.instance
        assertFalse(validInputs.all{invalidInputChecker1.isInvalid(it)})

        // Ensure singleton property maintained
        val invalidInputChecker2 = InvalidInput.instance
        assertSame(invalidInputChecker1, invalidInputChecker2)
    }
}