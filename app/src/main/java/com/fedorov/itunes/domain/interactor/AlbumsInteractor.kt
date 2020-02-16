package com.fedorov.itunes.domain.interactor

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.domain.DataMapper
import com.fedorov.itunes.ui.ItunesData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsInteractor @Inject constructor(
    private val repository: Repository,
    private val dataMapper: DataMapper
) {
    suspend fun getAlbums(albumName: String): List<ItunesData> {
        val data = repository.getAlbums(albumName = albumName)

        return dataMapper.mapEntityToAlbumsUIModel(data)
    }
}