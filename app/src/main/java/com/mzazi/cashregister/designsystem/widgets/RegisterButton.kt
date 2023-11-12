package com.mzazi.cashregister.designsystem.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mzazi.cashregister.presentation.models.RegisterUiAction


@Composable
fun RegisterButtons(
    action: RegisterUiAction,
    modifier: Modifier = Modifier,
    onClick:()->Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        if (action.text !=null){
            Text(
                text = action.text,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }else{
            action.content()
        }
    }
}