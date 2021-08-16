package com.servify.nytimesbestseller.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    private const val BASE_URL = "https://api.nytimes.com/"

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService : ApiService = getRetrofit().create(ApiService::class.java)
}