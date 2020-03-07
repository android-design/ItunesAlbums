package com.fedorov.itunes.data

import com.fedorov.itunes.data.cache.Cache
import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.data.network.Api
import com.fedorov.itunes.domain.repository.Repository
import java.lang.ref.SoftReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val softCache: SoftReference<Cache<Entity>>
) : Repository {

    override suspend fun getAlbums(albumName: String): Entity {
        // With new request should clean tracks cache.
        softCache.get()?.clear()

        return api.getAlbums(albumName = albumName)
    }

    override suspend fun getTracks(collectionId: Int): Entity {

        // If in cache has request data - return it from cache.
        softCache.get()?.get(collectionId)?.let {
            return it
        }

        val data =
            api.getTracks(collectionId = collectionId)

        softCache.get()?.put(collectionId = collectionId, value = data)

        return data
    }
}