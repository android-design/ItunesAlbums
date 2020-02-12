package com.fedorov.itunes.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.itunes.ui.Event
import com.fedorov.itunes.ui.ItunesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val eventData = MutableLiveData<Event>()

    fun makeRequest(
        liveData: MutableLiveData<Event>,
        request: suspend () -> List<ItunesData>?
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