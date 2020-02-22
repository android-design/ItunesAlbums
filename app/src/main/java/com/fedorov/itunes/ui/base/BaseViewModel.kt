package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<T> : ViewModel() {

    private var job: Job? = null

    fun <T> makeRequest(
        data: MutableLiveData<T>,
        showProgressBar: MutableLiveData<Boolean>,
        exception: MutableLiveData<Exception>,
        request: suspend () -> T?
    ) {
        cancelJob()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                showProgressBar.postValue(true)
                val response = request.invoke()
                Timber.d(response.toString())
                data.postValue(response)
            } catch (e: Exception) {
                when (e) {
                    !is CancellationException -> {
                        Timber.e(e)
                        exception.postValue(e)
                    }
                    else -> Timber.e(e)
                }

            } finally {
                showProgressBar.postValue(false)
            }
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