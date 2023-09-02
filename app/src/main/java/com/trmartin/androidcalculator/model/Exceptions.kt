package com.trmartin.androidcalculator.model

class Exceptions {
    class InvalidInput(message: String): Exception(message)
    class EmptyInput(message: String): Exception(message)

    class CalculationFailure(message: String): Exception(message)
}