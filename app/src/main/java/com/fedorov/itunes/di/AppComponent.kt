package com.fedorov.itunes.di

import dagger.Component
import com.fedorov.itunes.ui.albumInfo.AlbumInfoActivity
import com.fedorov.itunes.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, CacheModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: AlbumInfoActivity)
}