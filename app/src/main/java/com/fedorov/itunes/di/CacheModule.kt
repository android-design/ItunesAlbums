package com.fedorov.itunes.di

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.cache.CacheImpl
import com.fedorov.itunes.data.model.Entity
import dagger.Binds
import dagger.Module

/**
 * Dagger module for cache instance.
 */
@Module
abstract class CacheModule {
    @Binds
    abstract fun provideCache(cache: CacheImpl<Entity>): Cache<Entity>
}