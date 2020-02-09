package com.fedorov.itunes.data.model

import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("artistName")
        val artistName: String,
        @SerializedName("artworkUrl100") // picture
        val artworkUrl100: String,
        @SerializedName("collectionId") // album id
        val collectionId: Int,
        @SerializedName("collectionName") // album name
        val collectionName: String,
        @SerializedName("primaryGenreName") // genre
        val primaryGenreName: String,
        @SerializedName("releaseDate")
        val releaseDate: String,
        @SerializedName("trackCount")
        val trackCount: Int,
        @SerializedName("trackName")
        val trackName: String?,
        @SerializedName("trackNumber")
        val trackNumber: Int?,
        @SerializedName("trackTimeMillis") // tracks duration
        val trackTimeMillis: Int?,
        @SerializedName("discNumber")
        val discNumber: Int?
    )
}