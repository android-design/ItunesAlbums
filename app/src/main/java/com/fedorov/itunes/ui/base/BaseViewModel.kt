package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<T> : ViewModel() {
    fun <T> makeRequest(
        data: MutableLiveData<T>,
        showProgressBar: MutableLiveData<Boolean>,
        exception: MutableLiveData<Exception>,
        request: suspend () -> T
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            showProgressBar.postValue(true)
            val response = request.invoke()
            data.postValue(response)
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