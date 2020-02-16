package com.fedorov.itunes.cache

interface Cache<T> {
    fun clear()
    fun get(collectionId: Int): T?
    fun put(collectionId: Int, value: T)
}