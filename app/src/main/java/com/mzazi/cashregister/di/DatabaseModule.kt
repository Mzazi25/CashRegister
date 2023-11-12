package com.mzazi.cashregister.di

import android.content.Context
import androidx.room.Room
import com.mzazi.cashregister.data.cache.db.RegisterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesRegisterDatabase(@ApplicationContext context:Context): RegisterDatabase =
        Room.databaseBuilder(
            context,
            RegisterDatabase::class.java,
            "characterDb"
        ).build()

    @Provides
    @Singleton
    fun providesRegisterDao(db:RegisterDatabase) = db.registerDao()
}