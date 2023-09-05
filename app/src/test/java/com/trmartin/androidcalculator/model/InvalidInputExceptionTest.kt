package com.trmartin.androidcalculator.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InvalidInputExceptionTest {
    @Test
    fun `Regex for InvalidInput correctly identifies invalid user input`() {
        val validInput1 = "2 * 4 - 7.3 + .3 - 3."
        val validInput2 = ".2 * .4 - 7.3 + .3 - 3."
        val validInput3 = "2.00000 * 41111.222 - 7.3 + .3 - 3."
        val invalidInput1 = "2.3.4 * 4 - 7.3 + .3 - 3." // Multiple decimals in one argument
        val invalidInput2 = "2 * * 4 - 7.3 + .3 - 3." // Repeated operations without arguments
        val invalidInput3 = "2 * 4 - . + 7.3 + .3 - 3." // Lone decimal
        val invalidInput4 = "2 ** 4"

        val validInputs = arrayListOf<String>(validInput1, validInput2, validInput3)
        val invalidInputs = arrayListOf<String>(invalidInput1, invalidInput2, invalidInput3)

        val invalidInputChecker1 = InvalidInput.instance
        val invalidInputChecker2 = InvalidInput.instance
        assertTrue(validInputs.all{invalidInputChecker1.isInvalid(it)})
        assertFalse(invalidInputs.all{invalidInputChecker2.isInvalid(it)})
        // Ensure singleton property maintained
        assertSame(invalidInputChecker1, invalidInputChecker2)
    }
}