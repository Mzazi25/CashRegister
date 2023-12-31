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