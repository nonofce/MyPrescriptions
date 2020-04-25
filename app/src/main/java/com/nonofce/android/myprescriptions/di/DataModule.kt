package com.nonofce.android.myprescriptions.di

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.data.source.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun repositoryProvider(localDataSource: LocalDataSource): Repository =
        Repository(localDataSource)
}