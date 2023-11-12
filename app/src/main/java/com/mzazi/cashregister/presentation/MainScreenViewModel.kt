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

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _expressions = MutableStateFlow("")
    val expressions: StateFlow<String> = _expressions.asStateFlow()

    private val _expressionsList = MutableStateFlow(listOf<String>())
    val expressionsList: StateFlow<List<String>> = _expressionsList.asStateFlow()
    var summation = MutableStateFlow("")

    fun getRegisterValues(input: Double) {
        viewModelScope.launch {
            repository.getRegisterValues(input)
                .onStart {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                .catch { throwable ->
                    _state.value = _state.value.copy(
                        error = throwable
                    )
                }.collect { entity->
                    val toDomain =entity.asCoreModel()
                    _state.value = RegisterState(
                        input = toDomain.values
                    )
                }
        }
    }

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
                val number = BigDecimal(_expressions.value).setScale(2, RoundingMode.HALF_UP)
                _expressionsList.value = _expressionsList.value + number.toString()
                summation.value = calculateResult()
                _expressions.value = ""
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