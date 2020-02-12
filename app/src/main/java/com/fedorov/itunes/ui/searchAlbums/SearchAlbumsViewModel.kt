package com.fedorov.itunes.ui.searchAlbums

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel
import javax.inject.Inject

class SearchAlbumsViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    fun getAlbums(albumName: String) {
        makeRequest(eventData) {
            repository.getAlbums(albumName = albumName)
        }
    }
}