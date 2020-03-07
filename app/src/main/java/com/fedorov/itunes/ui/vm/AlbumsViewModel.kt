package com.fedorov.itunes.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.domain.base.UseCase
import com.fedorov.itunes.domain.interactor.GetAlbumInfoUseCase
import com.fedorov.itunes.domain.interactor.GetAlbumsUseCase
import com.fedorov.itunes.ui.mapper.toAlbums
import com.fedorov.itunes.ui.mapper.toTracks
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.base.BaseViewModel
import com.fedorov.itunes.ui.util.SingleLiveEvent
import kotlinx.coroutines.Job
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(
    private val getAlbumsUseCase: UseCase<String, Entity>,
    private val getAlbumInfoUseCase: UseCase<Int, Entity>
) :
    BaseViewModel<Entity>() {


    private val dataTest = MutableLiveData<Entity>()
    private val data = Transformations.map(
        dataTest
    ) {
        val albumsMapped = it.toAlbums()
        val tracksMapped = it.toTracks()

        listOf(albumsMapped, tracksMapped).flatten()
    }

    private val isShowProgressBar = MutableLiveData<Boolean>()
    private val exception = SingleLiveEvent<Exception>()

    private var job: Job? = null

    fun getShowPB(): LiveData<Boolean> = isShowProgressBar
    fun getException(): LiveData<Exception> = exception
    fun getData(): LiveData<List<ItunesData>> = data

    fun getAlbums(albumName: String) {
        makeRequest(
            dataTest, isShowProgressBar, exception
        ) {
            getAlbumsUseCase.execute(albumName)
        }
    }

    fun getTracks(collectionId: Int) {
        cancelJob()

        job = makeRequest(dataTest, isShowProgressBar, exception) {
            getAlbumInfoUseCase.execute(collectionId)
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