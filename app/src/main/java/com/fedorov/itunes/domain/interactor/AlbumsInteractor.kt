package com.fedorov.itunes.domain.interactor

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.domain.DataMapper
import com.fedorov.itunes.ui.ItunesData
import javax.inject.Inject

class AlbumsInteractor @Inject constructor(
    private val dataMapper: DataMapper
) {
    fun getAlbums(entity: Entity): List<ItunesData> {
        return dataMapper.mapEntityToAlbumsUIModel(entity)
    }

    fun getAlbumInfo(entity: Entity): List<ItunesData> {
        val albumsMapped = dataMapper.mapEntityToAlbumsUIModel(entity)
        val tracksMapped = dataMapper.mapEntityToTracksUIModel(entity)

        return listOf(albumsMapped, tracksMapped).flatten()
    }
}