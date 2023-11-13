/*
 * Copyright 2023 CashRegister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    suspend fun getRegisterEntries(): List<RegisterEntity>

    @Query("DELETE FROM RegisterEntity")
    suspend fun nukeCashRegister()
}
