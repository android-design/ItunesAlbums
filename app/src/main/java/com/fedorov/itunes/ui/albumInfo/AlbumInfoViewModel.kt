package com.fedorov.itunes.ui.albumInfo

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel
import kotlinx.coroutines.Job

class AlbumInfoViewModel : BaseViewModel() {
    // Coroutine will be canceled when ViewModel will be cleared.
    fun getTracks(collectionId: Int) {
        makeRequest(eventData) {
            Repository.getTracks(collectionId)
        }
    }
}