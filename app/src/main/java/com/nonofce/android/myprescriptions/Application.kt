package com.nonofce.android.myprescriptions

import android.app.Application
import com.nonofce.android.myprescriptions.di.AppComponent
import com.nonofce.android.myprescriptions.di.DaggerAppComponent

class Application : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
