package com.fedorov.itunes.ui.albumInfo

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel

class AlbumInfoViewModel(val repository: Repository) : BaseViewModel() {
    fun getTracks(collectionId: Int) {
        makeRequest(eventData) {
            repository.getTracks(collectionId)
        }
    }
}