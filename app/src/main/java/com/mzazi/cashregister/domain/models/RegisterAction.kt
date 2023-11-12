package com.mzazi.cashregister.domain.models

sealed interface RegisterAction{
    data class Number(val number:Int):RegisterAction
    data class Op(val operation: Operation):RegisterAction
    object Decimal:RegisterAction
    object Delete:RegisterAction
    object Clear:RegisterAction
}