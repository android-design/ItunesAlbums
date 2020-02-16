package com.fedorov.itunes.data

import com.fedorov.itunes.data.model.Entity

interface Repository {
    suspend fun getAlbums(albumName: String): Entity
    suspend fun getTracks(collectionId: Int): Entity
}