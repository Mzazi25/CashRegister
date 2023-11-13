/*
 * Copyright 2023 CashRegister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mzazi.cashregister.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzazi.cashregister.domain.models.RegisterAction
import com.mzazi.cashregister.domain.models.RegisterValues
import com.mzazi.cashregister.domain.models.operationSymbols
import com.mzazi.cashregister.domain.repo.RegisterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: RegisterRepo
) : ViewModel() {
    // Input expression for the calculator
    private val _expressions = MutableStateFlow("")
    val expressions: StateFlow<String> = _expressions.asStateFlow()

    // List of register values
    private val _expressionsList = MutableStateFlow(listOf<RegisterValues>())
    val expressionsList: StateFlow<List<RegisterValues>> = _expressionsList.asStateFlow()

    // Summation of all register values
    val summation = MutableStateFlow("")

    init {
        observeCachedData()
    }

    /*
    * Function to observe cached data from the repository and update expressionsList and summation
     */
    private fun observeCachedData() {
        viewModelScope.launch {
            repository.getRegisterValues()
                .collect { registerList ->
                    // Update expressionsList and calculate the new summation
                    _expressionsList.value = registerList
                    summation.value = calculateResult()
                }
        }
    }

    /*
    * This function handles all user actions for
    * Deleting a value
    * Adding a value
    * Inputting a number
    * Adding a decimal
    * Clearing data from the list
     */
    fun onRegisterAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.Delete -> {
                _expressions.value = _expressions.value.dropLast(1)
            }

            RegisterAction.Decimal -> {
                if (canEnterDecimal()) {
                    _expressions.value += "."
                }
            }
            is RegisterAction.Number -> {
                _expressions.value += action.number
            }
            is RegisterAction.Add -> {
                viewModelScope.launch {
                    val registerValues = RegisterValues(_expressions.value)
                    repository.insertRegisterValues(registerValues)
                    _expressions.value = ""
                }
            }
            is RegisterAction.Clear -> {
                viewModelScope.launch {
                    repository.nukeRegisterValues()
                }
            }
        }
    }

    // Check if a decimal point can be added to the current expression
    private fun canEnterDecimal(): Boolean {
        if (_expressions.value.isEmpty() || _expressions.value.last() in "$operationSymbols.()") {
            return false
        }
        return !_expressions.value.takeLastWhile {
            it in "0123456789."
        }.contains(".")
    }

    // Calculate the summation of all register values
    private fun calculateResult(): String {
        // Extract numeric values from the expressionsList and calculate the sum
        val numbers = _expressionsList.value.mapNotNull {
            it.values.toDoubleOrNull()
        }
        // If no register values, return 0.00
        if (_expressionsList.value.isEmpty()) {
            return 0.00.toString()
        }
        // Round the result to two decimal places
        val result = numbers.sum()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(result)
    }
}