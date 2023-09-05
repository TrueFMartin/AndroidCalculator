package com.trmartin.androidcalculator.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DelegatorTest {

    @Test
    fun calc() {
        val delegator = Delegator
        assert(delegator.calc("7 - 4 * 3 - 2 / 2").equals(-6.0))
        assert(delegator.calc("3 * 3 * -3 * -3").equals(81.0))
        assert(delegator.calc("-27 / -27").equals(1.0))
        assert(delegator.calc("-1 / 2 + 3 + 1").equals(3.5))

        assertThrows<Exceptions.InvalidInputException>{
            delegator.calc("3.3. - 4")
        }
        assertThrows<Exceptions.EmptyInput> {
            delegator.calc("")
        }
    }
}