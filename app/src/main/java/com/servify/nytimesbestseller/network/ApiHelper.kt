package com.servify.nytimesbestseller.network

class ApiHelper(val apiService: ApiService) {

    suspend fun getBestSellerList(apiKey : String) = apiService.getNYTBestSellerList(apiKey)
    suspend fun getBestSellerBooksList(apiKey: String, listName:String) = apiService.getNYTBookList(apiKey,listName)
}