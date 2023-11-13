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
package com.mzazi.cashregister.data.repo

import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.mapper.asCoreEntity
import com.mzazi.cashregister.domain.models.RegisterValues
import com.mzazi.cashregister.domain.repo.RegisterRepo
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Implementation of the [RegisterRepo] interface.
 * @property registerDao The DAO for accessing and managing cash register data in the local database.
 * @property dispatcher The CoroutineDispatcher to perform operations on.
 */

class RegisterRepoImpl @Inject constructor(
    private val registerDao: RegisterDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RegisterRepo {

    /**
     * Retrieves a flow of all Cash Register from the local database.
     * @return A flow emitting a list of [RegisterValues] objects representing the cash register.
     */
    override fun getRegisterValues(): Flow<List<RegisterValues>> {
        return flow {
            registerDao.getRegisterEntries().collect { entityList ->
                emit(entityList.map { it.asCoreEntity() })
            }
        }.flowOn(dispatcher)
    }

    // This function inserts RegisterValues to the database and runs on Dispatcher.IO
    override suspend fun insertRegisterValues(input: RegisterValues) =
        withContext(dispatcher) {
            registerDao.insertRegister(input.asCoreEntity())
        }

    // This function deletes all values from the database and runs on Dispatcher.IO
    override suspend fun nukeRegisterValues() =
        withContext(dispatcher) {
            registerDao.nukeCashRegister()
        }
}