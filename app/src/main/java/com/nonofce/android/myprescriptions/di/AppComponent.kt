package com.nonofce.android.myprescriptions.di

import android.app.Application
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionComponent
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionModule
import dagger.BindsInstance
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(
    modules = []
)
interface AppComponent {

    fun plus(module:PrescriptionModule): PrescriptionComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}