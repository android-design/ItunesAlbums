package com.fedorov.itunes.data

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.data.network.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val cache: Cache<Entity>
) : Repository {
    override suspend fun getAlbums(albumName: String): Entity {
        // With new request should clean tracks cache.
        cache.clear()

        return api.getAlbums(albumName = albumName)
    }

    override suspend fun getTracks(collectionId: Int): Entity {

        // If in cache has request data - return it from cache.
        cache.get(collectionId)?.let {
            return it
        }

        val data =
            api.getTracks(collectionId = collectionId)// dataMapper.mapItunesApiToUI(api.getTracks(collectionId))
        cache.put(collectionId = collectionId, value = data)

        return data
    }
}