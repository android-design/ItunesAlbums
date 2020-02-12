package com.fedorov.itunes.ui.albumInfo

import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.base.BaseViewModel
import javax.inject.Inject

class AlbumInfoViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    // Coroutine will be canceled when ViewModel will be cleared (depends on  ViewModel scope).
    fun getTracks(collectionId: Int) {
        makeRequest(eventData) {
            repository.getTracks(collectionId)
        }
    }
}