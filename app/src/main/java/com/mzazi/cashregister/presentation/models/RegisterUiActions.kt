package com.mzazi.cashregister.presentation.models

import com.mzazi.cashregister.domain.models.Operation
import com.mzazi.cashregister.domain.models.RegisterAction

val registerActions = listOf(
    RegisterUiAction(
        text = "1",
        action = RegisterAction.Number(1),
    ),
    RegisterUiAction(
        text = "2",
        action = RegisterAction.Number(2),
    ),
    RegisterUiAction(
        text = "3",
        action = RegisterAction.Number(3),
    ),
    RegisterUiAction(
        text = "4",
        action = RegisterAction.Number(4),
    ),
    RegisterUiAction(
        text = "5",
        action = RegisterAction.Number(5),
    ),
    RegisterUiAction(
        text = "6",
        action = RegisterAction.Number(6),
    ),
    RegisterUiAction(
        text = "7",
        action = RegisterAction.Number(7),
    ),
    RegisterUiAction(
        text = "8",
        action = RegisterAction.Number(8),
    ),
    RegisterUiAction(
        text = "9",
        action = RegisterAction.Number(9),
    ),
    RegisterUiAction(
        text = "0",
        action = RegisterAction.Number(0),
    ),
    RegisterUiAction(
        text = "ADD",
        action = RegisterAction.Op(Operation.ADD),
    ),
    RegisterUiAction(
        text = "DEL",
        action = RegisterAction.Delete,
    ),
    RegisterUiAction(
        text = ".",
        action = RegisterAction.Decimal,
    )
)