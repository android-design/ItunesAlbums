package com.fedorov.itunes.di

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.cache.CacheImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {
    @Binds
    abstract fun provideCache(cache: CacheImpl): Cache
}