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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mzazi.cashregister.designsystem.widgets.CashRegisterButtonGrid
import com.mzazi.cashregister.designsystem.widgets.CashRegisterDisplay
import com.mzazi.cashregister.designsystem.widgets.ListDisplay
import com.mzazi.cashregister.designsystem.widgets.TotalSummationWidget
import com.mzazi.cashregister.domain.models.RegisterAction
import com.mzazi.cashregister.domain.models.RegisterValues
import com.mzazi.cashregister.presentation.models.registerActions

@Composable
fun MainScreen(
    onAction: (RegisterAction) -> Unit,
    expression: String,
    expressionList: List<RegisterValues>,
    summation: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CashRegisterDisplay(
                expression = "KSH $expression",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 25.dp,
                            bottomEnd = 25.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(
                        vertical = 64.dp,
                        horizontal = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(12.dp))
            CashRegisterButtonGrid(
                actions = registerActions,
                onAction = onAction,
                modifier = Modifier
                    .padding(8.dp)
            )
            ListDisplay(
                operations = expressionList,
                modifier = Modifier.weight(1f)
            )
            Divider()
            TotalSummationWidget(
                modifier = Modifier.padding(16.dp),
                text = "Total $summation"
            )
        }
    }
}