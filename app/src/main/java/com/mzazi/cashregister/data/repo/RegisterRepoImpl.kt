package com.mzazi.cashregister.data.repo

import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.cache.db.RegisterDatabase
import com.mzazi.cashregister.data.cache.models.RegisterEntity
import com.mzazi.cashregister.domain.repo.RegisterRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class RegisterRepoImpl @Inject constructor(
    private val registerDao:RegisterDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): RegisterRepo{
    override fun getRegisterValues(input: Double): Flow<RegisterEntity> = flow {
        try {
            val register = RegisterEntity(
                values = input
            )
            registerDao.insertRegister(register)
        }catch (e:Exception){
            // Something went wrong with the query to the Database
            // Log the error and proceed
            Timber.e("Exception---------------$e")
        }
        emit(registerDao.getRegisterEntries())
    }.flowOn(dispatcher)
}