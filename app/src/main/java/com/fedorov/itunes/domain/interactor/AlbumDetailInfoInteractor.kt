package com.fedorov.itunes.domain.interactor

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.domain.DataMapper
import com.fedorov.itunes.ui.ItunesData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumDetailInfoInteractor @Inject constructor(
    private val repository: Repository,
    private val dataMapper: DataMapper
) {
    suspend fun getAlbumInfo(collectionId: Int): List<ItunesData> {
        val data = repository.getTracks(collectionId = collectionId)

        val albumsMapped = dataMapper.mapEntityToAlbumsUIModel(data)
        val tracksMapped = dataMapper.mapEntityToTracksUIModel(data)

        return listOf(albumsMapped, tracksMapped).flatten()
    }
}