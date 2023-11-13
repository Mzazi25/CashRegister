package com.mzazi.cashregister.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mzazi.cashregister.data.cache.models.RegisterEntity

@Dao
interface RegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegister(registerEntity: RegisterEntity)

    @Query("SELECT * FROM RegisterEntity")
    suspend fun getRegisterEntries(): List<RegisterEntity>

    @Query("DELETE FROM RegisterEntity")
    suspend fun nukeCashRegister()
}