package com.trmartin.androidcalculator.model

class Exceptions{
    // Exceptions found from regex in 'InvalidInput' class and other checks
    class LoneDecimalException(message: String): Exception(message)
    class MultipleDecimalException(message: String): Exception(message)
    class MultipleOperatorException(message: String): Exception(message)
    class EmptyInput(message: String): Exception(message)
    class CalculationFailure(message: String): Exception(message)
    class EndsInOperatorException(message: String): Exception(message)
    class StartsWithOperatorException(message: String): Exception(message)
}