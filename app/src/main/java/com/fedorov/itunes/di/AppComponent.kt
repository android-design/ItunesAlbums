package com.fedorov.itunes.di

import com.fedorov.itunes.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, SearchAlbumsModule::class, AlbumInfoModule::class, RepositoryModule::class, NetModule::class, CacheModule::class, UseCaseModule::class])
interface AppComponent : AndroidInjector<App>