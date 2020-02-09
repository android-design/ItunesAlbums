package com.fedorov.itunes.di

import com.fedorov.itunes.data.network.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetModule {
    @Provides
    fun getOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder() // Increase the timeouts for slow connections(ex. EDGE)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    fun getRetrofitService(okHttpClient: OkHttpClient): Api {
        val baseUrl = "https://itunes.apple.com/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}