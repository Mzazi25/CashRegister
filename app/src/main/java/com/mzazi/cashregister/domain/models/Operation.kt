package com.mzazi.cashregister.domain.models

enum class Operation(val symbol:String){
    ADD("+"),
}
val operationSymbols = Operation.values().map { it.symbol }.joinToString("")
