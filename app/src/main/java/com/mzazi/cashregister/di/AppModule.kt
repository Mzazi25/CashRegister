package com.mzazi.cashregister.di

import android.content.Context
import com.mzazi.cashregister.CashRegisterApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApplication(@ApplicationContext app: Context): CashRegisterApp =
        app as CashRegisterApp
}