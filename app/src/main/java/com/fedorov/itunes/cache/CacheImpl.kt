package com.fedorov.itunes.cache

import javax.inject.Inject

class CacheImpl<T> @Inject constructor() : Cache<T> {
    private val tracksCache: MutableMap<Int, T?> = HashMap()

    override fun clear() {
        tracksCache.clear()
    }

    override fun get(collectionId: Int): T? {
        return tracksCache[collectionId]
    }

    override fun put(collectionId: Int, value: T) {
        tracksCache[collectionId] = value
    }
}