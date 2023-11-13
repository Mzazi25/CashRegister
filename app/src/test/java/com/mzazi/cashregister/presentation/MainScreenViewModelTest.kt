package com.mzazi.cashregister.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.mzazi.cashregister.domain.models.RegisterValues
import com.mzazi.cashregister.domain.repo.RegisterRepo
import com.mzazi.cashregister.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainScreenViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: RegisterRepo = mockk()

    @Test
    fun `test initial state of the viewmodel when there's a list of values`() = runBlocking {
        val mockRegisterValuesList = listOf(
            RegisterValues("10.0"),
            RegisterValues("5.5"),
            RegisterValues("7.5")
        )
        coEvery { repository.getRegisterValues() } returns flowOf(mockRegisterValuesList)
        val viewModel = MainScreenViewModel(repository)
        viewModel.expressions.test {
            Truth.assertThat(awaitItem()).isEqualTo("")
        }

        viewModel.expressionsList.test {
            Truth.assertThat(awaitItem().size).isEqualTo(3)
        }
        viewModel.summation.test {
            Truth.assertThat(awaitItem()).isEqualTo("23")
        }

    }
}