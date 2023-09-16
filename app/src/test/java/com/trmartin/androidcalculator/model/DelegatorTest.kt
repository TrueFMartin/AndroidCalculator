package com.trmartin.androidcalculator.model

import com.trmartin.androidcalculator.Contract
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DelegatorTest {

    @Test
    fun calc() {
        val delegator: Contract.Model = Delegator
        assert(delegator.calc("7 - 4 * 3 - 2 / 2").equals(-6.0))
        assert(delegator.calc("3 * 3 * -3 * -3").equals(81.0))
        assert(delegator.calc("-27 / -27").equals(1.0))
        assert(delegator.calc("-1 / 2 + 3 + 1").equals(3.5))

        assertThrows<Exceptions.LoneDecimalException>{
            delegator.calc("3.3 - . + 4")
        }
        assertThrows<Exceptions.MultipleDecimalException>{
            delegator.calc("3.3.3 - 4")
        }
        assertThrows<Exceptions.EmptyInput> {
            delegator.calc("")
        }
        assertThrows<Exceptions.StartsWithOperatorException> {
            delegator.calc(" * 7 - 3")
        }
    }
}