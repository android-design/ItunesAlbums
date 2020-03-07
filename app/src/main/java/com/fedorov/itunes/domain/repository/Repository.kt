package com.fedorov.itunes.domain.repository

import com.fedorov.itunes.data.model.Entity

interface Repository {
    suspend fun getAlbums(albumName: String): Entity
    suspend fun getTracks(collectionId: Int): Entity
}