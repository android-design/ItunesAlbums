package com.fedorov.itunes.cache

import com.fedorov.itunes.ui.ItunesData

interface Cache {
    fun clear()
    fun get(collectionId: Int): List<ItunesData>?
    fun put(collectionId: Int, value: List<ItunesData>)
}