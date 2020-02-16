package com.fedorov.itunes.ui.searchAlbums

import com.fedorov.itunes.domain.interactor.AlbumsInteractor
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.base.BaseViewModel
import javax.inject.Inject

class SearchAlbumsViewModel @Inject constructor(private val albumsInteractor: AlbumsInteractor) :
    BaseViewModel<List<ItunesData>>() {

    fun getAlbums(albumName: String) {
        makeRequest(eventData) {
            albumsInteractor.getAlbums(albumName = albumName)
        }
    }
}