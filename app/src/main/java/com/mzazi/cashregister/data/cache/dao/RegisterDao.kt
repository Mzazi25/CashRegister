package com.mzazi.cashregister.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mzazi.cashregister.data.cache.models.RegisterEntity

@Dao
interface RegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegister(registerEntity: RegisterEntity)

    @Query("SELECT * FROM RegisterEntity")
    fun getRegisterEntries(): RegisterEntity
}