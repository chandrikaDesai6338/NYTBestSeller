package com.servify.nytimesbestseller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.servify.nytimesbestseller.network.ApiHelper

class ViewModelFactory(val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(BestSellerViewModel::class.java)){
           return BestSellerViewModel(apiHelper) as T
       }
        throw IllegalAccessException("Class Name Not Found")
    }
}