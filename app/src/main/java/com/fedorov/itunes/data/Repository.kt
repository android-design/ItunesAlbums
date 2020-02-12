package com.fedorov.itunes.data

import com.fedorov.itunes.cache.Cache
import com.fedorov.itunes.cache.CacheImpl
import com.fedorov.itunes.data.network.Api
import com.fedorov.itunes.ui.ItunesData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//class Repository(
//    private val api: Api,
//    private val dataMapper: DataMapper,
//    private val cache: Cache
//) {
object Repository {
    val okHttpClient =
        OkHttpClient.Builder() // Increase the timeouts for slow connections(ex. EDGE)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    val baseUrl = "https://itunes.apple.com/"

    val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    val cache = CacheImpl()

    val dataMapper = DataMapper()

    suspend fun getAlbums(albumName: String): List<ItunesData>? {
        // With new request should clean tracks cache.
        cache.clear()

        return dataMapper.mapItunesApiToUI(api.getAlbums(albumName))
    }

    suspend fun getTracks(collectionId: Int): List<ItunesData>? {

        // If in cache has request data - return it from cache.
        cache.get(collectionId)?.let {
            return it
        }

        val data = dataMapper.mapItunesApiToUI(api.getTracks(collectionId))
        data?.let {
            cache.put(collectionId = collectionId, value = it)
        }

        return data
    }
}