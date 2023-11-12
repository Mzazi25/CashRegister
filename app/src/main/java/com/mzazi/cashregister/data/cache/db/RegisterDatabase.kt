package com.mzazi.cashregister.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.cache.models.RegisterEntity

@Database(
    entities = [RegisterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RegisterDatabase :RoomDatabase(){
    abstract fun registerDao(): RegisterDao
}