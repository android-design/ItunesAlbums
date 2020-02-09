package com.fedorov.itunes.data.network

import com.fedorov.itunes.data.model.Entity
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search?media=music&entity=album&attribute=albumTerm")
    suspend fun getAlbums(@Query("term") albumName: String): Entity

    @GET("lookup?media=music&entity=song")
    suspend fun getTracks(@Query(value = "id") collectionId: Int): Entity
}


