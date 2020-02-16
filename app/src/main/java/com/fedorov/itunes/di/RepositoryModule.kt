package com.fedorov.itunes.di

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.data.RepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCache(repository: RepositoryImpl): Repository
}