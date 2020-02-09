package com.fedorov.itunes.ui.main

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel

class MainActivityViewModel(val repository: Repository) : BaseViewModel() {
    fun getAlbums(albumName: String) {
        makeRequest(eventData) {
            repository.getAlbums(albumName = albumName)
        }
    }
}