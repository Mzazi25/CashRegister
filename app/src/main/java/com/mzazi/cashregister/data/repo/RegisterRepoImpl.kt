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
import com.mzazi.cashregister.data.cache.models.RegisterEntity
import com.mzazi.cashregister.domain.repo.RegisterRepo
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RegisterRepoImpl @Inject constructor(
    private val registerDao: RegisterDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RegisterRepo {

    override fun insertAndGetValues(input: RegisterEntity): Flow<List<RegisterEntity>> = flow {
        try {
            registerDao.nukeCashRegister()
            registerDao.insertRegister(input)
        } catch (e: Exception) {
            Timber.e("Exception caught---------------$e")
        }
        emit(registerDao.getRegisterEntries())
    }.flowOn(dispatcher)
}
