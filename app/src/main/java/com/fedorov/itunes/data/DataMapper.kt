package com.fedorov.itunes.data

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.ui.Album
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Track
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataMapper @Inject constructor() {

    companion object {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputDateFormat = SimpleDateFormat("yyyy", Locale.US)
    }

    fun mapItunesApiToUI(model: Entity): List<ItunesData>? {
        val sortedModel =
            model.results.sortedWith(compareBy({ it.trackNumber }, { it.collectionName }))

        return  sortedModel.map {
            when {
                it.trackName != null ->
                    Track(
                        it.collectionName,
                        it.artistName,
                        it.artworkUrl100,
                        it.primaryGenreName,
                        localDate(it.releaseDate),
                        it.trackNumber!!,
                        it.trackName,
                        it.trackTimeMillis!!
                    )
                else -> Album(
                    it.collectionId,
                    it.collectionName,
                    it.artistName,
                    it.artworkUrl100,
                    it.primaryGenreName,
                    localDate(it.releaseDate),
                    it.trackCount
                )
            }
        }
    }

    private fun localDate(sourceString: String): String {
        val dateSource = inputDateFormat.parse(sourceString)
        return outputDateFormat.format(dateSource!!)
    }
}