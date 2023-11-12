package com.mzazi.cashregister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzazi.cashregister.designsystem.theme.CashRegisterTheme
import com.mzazi.cashregister.presentation.MainScreen
import com.mzazi.cashregister.presentation.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainScreenViewModel = hiltViewModel()
            val expression by viewModel.expressions.collectAsStateWithLifecycle()
            val expressionList by viewModel.expressionsList.collectAsStateWithLifecycle()
            val summation by viewModel.summation.collectAsStateWithLifecycle()

            CashRegisterTheme {
                MainScreen(
                    onAction = viewModel::onRegisterAction,
                    expression = expression,
                    expressionList = expressionList,
                    summation = summation
                )
            }
        }
    }
}
