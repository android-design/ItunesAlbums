package com.fedorov.itunes.domain

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.ui.Album
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Track
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun mapEntityToAlbumsUIModel(model: Entity): List<ItunesData> {
        val sortedAlbums =
            model.results.filter { it.trackName == null }.sortedBy { it.collectionName }

        return sortedAlbums.map {
            Album(
                it.collectionId,
                it.collectionName,
                it.artistName,
                it.artworkUrl100,
                it.primaryGenreName,
                localDate(it.releaseDate),
                it.trackCount.toString()
            )
        }
    }

    fun mapEntityToTracksUIModel(model: Entity): List<ItunesData> {
        val sortedTracks =
            model.results.filter { it.trackName != null }.sortedBy { it.trackNumber }

        return sortedTracks.map {
            Track(
                it.collectionName,
                it.artistName,
                it.artworkUrl100,
                it.primaryGenreName,
                localDate(it.releaseDate),
                it.trackNumber?.toString() ?: "",
                it.trackName ?: "",
                it.trackTimeMillis?.toString() ?: ""
            )
        }
    }

    private fun localDate(sourceString: String): String {
        val dateSource = inputDateFormat.parse(sourceString)

        dateSource?.let {
            return outputDateFormat.format(dateSource)
        }

        return EMPTY_STRING
    }

    companion object {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputDateFormat = SimpleDateFormat("yyyy", Locale.US)

        const val EMPTY_STRING = ""
    }
}