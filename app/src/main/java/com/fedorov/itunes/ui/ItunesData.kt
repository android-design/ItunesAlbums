package com.fedorov.itunes.ui

sealed class ItunesData

data class Album(
    val collectionId: Int,
    val collectionName: String,
    val artistName: String,
    val image: String,
    val genre: String,
    val releaseDate: String,
    val trackCount: String
) : ItunesData()

data class Track(
    val collectionName: String,
    val artistName: String,
    val image: String,
    val genre: String,
    val releaseDate: String,
    val trackNumber: String,
    val trackName: String,
    val trackTimeMillis: String
) : ItunesData()