package com.fedorov.itunes.ui.mapper

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.ui.Album
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Track
import java.text.SimpleDateFormat
import java.util.*


val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
val outputDateFormat = SimpleDateFormat("yyyy", Locale.US)

fun Entity.toAlbums(): List<ItunesData> {
    val sortedAlbums =
        results.filter { it.trackName == null }.sortedBy { it.collectionName }

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

fun Entity.toTracks(): List<ItunesData> {
    val sortedTracks =
        results.filter { it.trackName != null }.sortedBy { it.trackNumber }

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

    return dateSource?.let {
        outputDateFormat.format(dateSource)
    } ?: ""
}