package com.fedorov.itunes.ui.albumInfo

import com.fedorov.itunes.domain.interactor.AlbumDetailInfoInteractor
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.base.BaseViewModel
import javax.inject.Inject

class AlbumInfoViewModel @Inject constructor(private val albumDetailInfoInteractor: AlbumDetailInfoInteractor) :
    BaseViewModel<List<ItunesData>>() {
    fun getTracks(collectionId: Int) {
        makeRequest(eventData) {
            albumDetailInfoInteractor.getAlbumInfo(collectionId = collectionId)
        }
    }
}