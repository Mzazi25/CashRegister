package com.mzazi.cashregister.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzazi.cashregister.data.mapper.asCoreModel
import com.mzazi.cashregister.domain.models.Operation
import com.mzazi.cashregister.domain.models.RegisterAction
import com.mzazi.cashregister.domain.models.operationSymbols
import com.mzazi.cashregister.domain.repo.RegisterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: RegisterRepo
) : ViewModel() {

    private val _mutableDouble = MutableStateFlow(0.00)
    val doubleState: StateFlow<Double> = _mutableDouble.asStateFlow()

    private val _expressions = MutableStateFlow("")
    val expressions: StateFlow<String> = _expressions.asStateFlow()

    private val _expressionsList = MutableStateFlow(listOf<String>())
    val expressionsList: StateFlow<List<String>> = _expressionsList.asStateFlow()

    var summation = MutableStateFlow("")

    fun onRegisterAction(action:RegisterAction){
        when(action){
            is RegisterAction.Delete -> {
                _expressions.value = _expressions.value.dropLast(1)
            }

            RegisterAction.Decimal -> {
                if (canEnterDecimal()){
                    _expressions.value +="."
                }
            }
            is RegisterAction.Number -> {
                _expressions.value +=action.number
            }
            is RegisterAction.Op -> {
                _expressionsList.value = _expressionsList.value + _expressions.value
                summation.value = calculateResult()
                _expressions.value = ""
//
            }
            is RegisterAction.Clear -> {
                _expressionsList.value = emptyList()
                summation.value = 0.00.toString()
            }
        }
    }

    private fun canEnterDecimal():Boolean{
        if (_expressions.value.isEmpty() || _expressions.value.last() in "$operationSymbols.()") {
            return false
        }
        return !_expressions.value.takeLastWhile {
            it in "0123456789."
        }.contains(".")
    }


    private fun calculateResult(): String {
        val numbers = _expressionsList.value.mapNotNull { it.toDoubleOrNull() }
        val result = numbers.sum()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(result)
    }
}