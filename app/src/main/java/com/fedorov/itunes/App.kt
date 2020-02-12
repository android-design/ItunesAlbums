package com.fedorov.itunes

import android.app.Application
import com.fedorov.itunes.di.AppComponent
import com.fedorov.itunes.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}