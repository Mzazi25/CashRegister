package com.mzazi.cashregister.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mzazi.cashregister.domain.models.RegisterAction
import com.mzazi.cashregister.presentation.models.RegisterUiAction


@Composable
fun CashRegisterButtonGrid(
    actions:List<RegisterUiAction>,
    onAction:(RegisterAction)->Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier=modifier,
        columns = GridCells.Fixed(4),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(actions){action->
                RegisterButtons(
                    action = action,
                    modifier = Modifier.aspectRatio(1f),
                    onClick = {onAction(action.action)}
                )
            }
        }
    )
}