package com.mzazi.cashregister.presentation.models

import androidx.compose.runtime.Composable
import com.mzazi.cashregister.domain.models.RegisterAction

data class RegisterUiAction(
    val text:String?,
    val action: RegisterAction,
    val content:@Composable ()->Unit = {}
)