package com.nonofce.android.myprescriptions.di

import android.app.Application
import androidx.room.Room
import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.myprescriptions.data.LocalDatabase
import com.nonofce.android.myprescriptions.data.RoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): LocalDatabase =
        Room.databaseBuilder(app.applicationContext, LocalDatabase::class.java, "prescription_db")
            .build()

    @Provides
    @Singleton
    fun localDataSourceProvider(database: LocalDatabase): LocalDataSource = RoomDataSource(database)

}