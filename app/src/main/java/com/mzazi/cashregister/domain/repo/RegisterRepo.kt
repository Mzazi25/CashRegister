package com.mzazi.cashregister.domain.repo

import com.mzazi.cashregister.data.cache.models.RegisterEntity
import kotlinx.coroutines.flow.Flow

interface RegisterRepo {
    fun getRegisterValues(input:Double):Flow<RegisterEntity>
}