package com.nonofce.android.myprescriptions.di

import android.app.Application
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionDataComponent
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionDataModule
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionListComponent
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionListModule
import dagger.BindsInstance
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(
    modules = [LocalStorageModule::class, DataModule::class]
)
interface AppComponent {

    fun plus(module: PrescriptionListModule): PrescriptionListComponent
    fun plus(module: PrescriptionDataModule): PrescriptionDataComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}