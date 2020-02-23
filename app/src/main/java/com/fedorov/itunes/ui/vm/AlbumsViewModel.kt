package com.fedorov.itunes.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fedorov.itunes.domain.interactor.AlbumsInteractor
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.base.BaseViewModel
import com.fedorov.itunes.util.SingleLiveEvent
import kotlinx.coroutines.Job
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(private val albumsInteractor: AlbumsInteractor) :
    BaseViewModel<List<ItunesData>>() {

    private val data = MutableLiveData<List<ItunesData>>()
    private val isShowProgressBar = MutableLiveData<Boolean>()
    private val exception = SingleLiveEvent<Exception>()

    private var job: Job? = null

    fun getShowPB(): LiveData<Boolean> = isShowProgressBar
    fun getException(): LiveData<Exception> = exception
    fun getData(): LiveData<List<ItunesData>> = data

    fun getAlbums(albumName: String) {
        makeRequest(data, isShowProgressBar, exception) {
            albumsInteractor.getAlbums(albumName = albumName)
        }
    }

    fun getTracks(collectionId: Int) {
        cancelJob()

        job = makeRequest(data, isShowProgressBar, exception) {
            albumsInteractor.getAlbumInfo(collectionId = collectionId)
        }
    }

    fun cancelJob() {
        job?.let {
            if (!it.isCancelled) {
                it.cancel()
            }
        }
    }
}