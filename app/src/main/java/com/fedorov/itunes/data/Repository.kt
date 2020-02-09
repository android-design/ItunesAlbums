package com.fedorov.itunes.data

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.data.network.Api
import com.fedorov.itunes.ui.ItunesData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: Api,
    private val dataMapper: DataMapper,
    private val cache: Cache
) {

    suspend fun getAlbums(albumName: String): List<ItunesData>? {
        // With new request should clean tracks cache.
        cache.clear()

        return dataMapper.mapItunesApiToUI(api.getAlbums(albumName))
    }

    suspend fun getTracks(collectionId: Int): List<ItunesData>? {

        // If in cache has request data - return it from cache.
        cache.get(collectionId)?.let {
            return it
        }

        val data = dataMapper.mapItunesApiToUI(api.getTracks(collectionId))
        data?.let {
            cache.put(collectionId = collectionId, value = it)
        }

        return data
    }
}