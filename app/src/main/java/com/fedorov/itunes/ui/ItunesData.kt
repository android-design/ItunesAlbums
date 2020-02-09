package com.fedorov.itunes.ui

sealed class ItunesData

data class Album(
    val collectionId: Int,
    val collectionName: String,
    val artistName: String,
    val image: String,
    val genre: String,
    val releaseDate: String,
    val trackCount: Int
) : ItunesData()

data class Track(
    val collectionName: String,
    val artistName: String,
    val image: String,
    val genre: String,
    val releaseDate: String,
    val trackNumber: Int,
    val trackName: String,
    val trackTimeMillis: Int
) : ItunesData()