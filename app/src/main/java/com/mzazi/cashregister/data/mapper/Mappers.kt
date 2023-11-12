package com.mzazi.cashregister.data.mapper

import com.mzazi.cashregister.data.cache.models.RegisterEntity
import com.mzazi.cashregister.domain.models.RegisterValues

fun RegisterEntity.asCoreModel(): RegisterValues =
    RegisterValues(values = values)