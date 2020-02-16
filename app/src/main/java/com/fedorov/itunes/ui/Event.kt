package com.fedorov.itunes.ui

import java.lang.Exception

data class Event<T>(val status: Status, val data: T?, val error: Exception?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Exception?): Event<T> {
            return Event(Status.ERROR, null, error)
        }
    }
}