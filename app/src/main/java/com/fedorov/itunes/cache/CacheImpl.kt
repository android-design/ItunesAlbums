package com.fedorov.itunes.cache

import com.fedorov.itunes.ui.ItunesData
import javax.inject.Inject

class CacheImpl @Inject constructor() : Cache {
    private val tracksCache: MutableMap<Int, List<ItunesData>?> = HashMap()

    override fun clear() {
        tracksCache.clear()
    }

    override fun get(collectionId: Int): List<ItunesData>? {
        return tracksCache[collectionId]
    }

    override fun put(collectionId: Int, value: List<ItunesData>) {
        tracksCache[collectionId] = value
    }
}