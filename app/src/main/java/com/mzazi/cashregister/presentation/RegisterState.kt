package com.mzazi.cashregister.presentation

data class RegisterState(
    val input: Double? = null,
    val error:Throwable? = null,
    val isLoading:Boolean = false
)