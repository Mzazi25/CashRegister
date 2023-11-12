package com.mzazi.cashregister.di

import com.mzazi.cashregister.data.cache.dao.RegisterDao
import com.mzazi.cashregister.data.repo.RegisterRepoImpl
import com.mzazi.cashregister.domain.repo.RegisterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesRepository(
        registerDao: RegisterDao
    ): RegisterRepo =
        RegisterRepoImpl(
            registerDao = registerDao
        )
}