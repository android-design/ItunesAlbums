package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<E, T> : ViewModel() {
    fun <T> makeRequest(
        data: MutableLiveData<T>,
        showProgressBar: MutableLiveData<Boolean>,
        exception: MutableLiveData<Exception>,
        request: suspend () -> E,
        makeBL: suspend (entity: E) -> T?
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            showProgressBar.postValue(true)
            val response = request.invoke()
            Timber.d(response.toString())
            val parsedResponse = makeBL.invoke(response)
            data.postValue(parsedResponse)
        } catch (e: Exception) {
            Timber.e(e)
            when (e) {
                !is CancellationException -> {
                    exception.postValue(e)
                }
            }
        } finally {
            showProgressBar.postValue(false)
        }
    }
}