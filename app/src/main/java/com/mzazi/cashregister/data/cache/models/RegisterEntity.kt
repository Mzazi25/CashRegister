package com.mzazi.cashregister.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegisterEntity(
    @PrimaryKey
    val values:String
)
