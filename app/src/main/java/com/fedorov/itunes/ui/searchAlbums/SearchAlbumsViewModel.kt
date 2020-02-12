package com.fedorov.itunes.ui.searchAlbums

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel

class SearchAlbumsViewModel : BaseViewModel() {

    fun getAlbums(albumName: String) {
        makeRequest(eventData) {
            Repository.getAlbums(albumName = albumName)
        }
    }
}