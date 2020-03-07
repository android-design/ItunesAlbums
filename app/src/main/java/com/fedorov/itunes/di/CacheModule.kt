package com.fedorov.itunes.di

import com.fedorov.itunes.data.cache.Cache
import com.fedorov.itunes.data.cache.CacheImpl
import com.fedorov.itunes.data.model.Entity
import dagger.Module
import dagger.Provides
import java.lang.ref.SoftReference

/**
 * Dagger module for cache instance.
 */
@Module
class CacheModule {
    @Provides
    fun provideCache(): SoftReference<Cache<Entity>> = SoftReference(CacheImpl())
}