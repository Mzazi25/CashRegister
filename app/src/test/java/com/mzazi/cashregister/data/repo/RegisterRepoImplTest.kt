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
    private val registerRepo: RegisterRepo = RegisterRepoImpl(registerDao, UnconfinedTestDispatcher())

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