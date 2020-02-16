package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.itunes.ui.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    val eventData = MutableLiveData<Event<T>>()

    fun <T> makeRequest(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> T?
    ) {
        liveData.postValue(Event.loading())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                liveData.postValue(Event.success(response))
            } catch (e: Exception) {
                liveData.postValue(Event.error(e))
            }
        }
    }
}