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

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.cache.models.RegisterEntity
import com.mzazi.cashregister.domain.repo.RegisterRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RegisterRepoImplTest {

    private val registerDao: RegisterDao = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val registerRepo: RegisterRepo = RegisterRepoImpl(
        registerDao,
        UnconfinedTestDispatcher()
    )

    @Test
    fun `get all Cash Register should emit non empty list`() = runTest {
        val registerEntity = listOf(
            RegisterEntity("10"),
            RegisterEntity("20"),
            RegisterEntity("30")
        )
        coEvery { registerDao.getRegisterEntries() } returns flowOf(registerEntity)
        registerRepo.getRegisterValues().test {
            Truth.assertThat(awaitItem().size).isEqualTo(3)
            awaitComplete()
        }
    }
}