package com.servify.nytimesbestseller.network

import com.servify.nytimesbestseller.model.BestSeller
import com.servify.nytimesbestseller.model.BestSellerBookList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("svc/books/v3/lists/names.json")
    suspend fun getNYTBestSellerList(@Query("api-key") apikey : String) : BestSeller

    @GET("svc/books/v3/lists.json")
    suspend fun getNYTBookList(@Query("api-key") apikey : String,
                               @Query("list") listName : String, ) : BestSellerBookList
}