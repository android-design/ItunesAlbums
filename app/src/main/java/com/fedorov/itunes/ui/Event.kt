package com.fedorov.itunes.ui

import java.lang.Exception

data class Event(val status: Status, val data: List<ItunesData>?, val error: Exception?) {

    companion object {
        fun loading(): Event {
            return Event(Status.LOADING, null, null)
        }

        fun success(data: List<ItunesData>?): Event {
            return Event(Status.SUCCESS, data, null)
        }

        fun error(error: Exception?): Event {
            return Event(Status.ERROR, null, error)
        }
    }
}