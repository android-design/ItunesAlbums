package com.fedorov.itunes.ui.albumInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fedorov.itunes.data.Repository

class AlbumInfoViewModelFactory (private val repository: Repository): ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlbumInfoViewModel(repository) as T
    }
}