package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Event

abstract class BaseViewModel : ViewModel() {
    val eventData = MutableLiveData<Event>()

    fun makeRequest(
        liveData: MutableLiveData<Event>,
        request: suspend () -> List<ItunesData>?
    ) {
        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                liveData.postValue(Event.success(response))
            } catch (e: Exception) {
                liveData.postValue(Event.error(e))
            }
        }
    }
}