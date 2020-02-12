package com.fedorov.itunes.di

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.cache.CacheImpl
import dagger.Binds
import dagger.Module

/**
 * Dagger module for cache instance.
 */
@Module
abstract class CacheModule {
    @Binds
    abstract fun provideCache(cache: CacheImpl): Cache
}