package com.mzazi.cashregister.data.cache.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.cache.models.RegisterEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterDatabaseTest {

    private lateinit var dao: RegisterDao
    private lateinit var db: RegisterDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RegisterDatabase::class.java
        ).build()
        dao = db.registerDao()
    }

    @Test
    fun `adding Register inputs to db returns valid data on query`() = runTest {
        val entity = RegisterEntity("12")
        val anotherEntity = RegisterEntity("20.01")
        dao.insertRegister(entity)
        dao.insertRegister(anotherEntity)
        val registerEntries = dao.getRegisterEntries().first()
        Truth.assertThat(registerEntries.size).isEqualTo(2)
    }

    @Test
    fun `deleting Cash Register from db updates properly`() = runTest {
        val entity = RegisterEntity("12")
        val anotherEntity = RegisterEntity("20.01")
        dao.insertRegister(entity)
        dao.insertRegister(anotherEntity)
        val registerEntries = dao.getRegisterEntries().first()
        Truth.assertThat(registerEntries.size).isEqualTo(2)
        dao.nukeCashRegister()
        Truth.assertThat(registerEntries.isEmpty())
    }
}