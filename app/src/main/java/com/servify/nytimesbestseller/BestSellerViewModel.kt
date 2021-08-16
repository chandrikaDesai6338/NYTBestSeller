package com.servify.nytimesbestseller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.servify.nytimesbestseller.model.Resource
import com.servify.nytimesbestseller.model.Result
import com.servify.nytimesbestseller.network.ApiHelper
import kotlinx.coroutines.Dispatchers

class BestSellerViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    fun getBestSellerList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getBestSellerList("z5nnAf9mwyqEta2OjxgvOJiLA6gLCfDM")))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.localizedMessage ?: "Network Error!!"))
        }
    }

    fun getBestSellerBooksList(listName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = apiHelper.getBestSellerBooksList(
                        "z5nnAf9mwyqEta2OjxgvOJiLA6gLCfDM",
                        listName
                    )
                )
            )
        } catch (e: Exception){
            Log.d("BestSellerViewModel", e.localizedMessage ?: "Network Error!!")
            emit(Resource.error(data = null, message = "Network Error!!"))
        }
    }

    fun sortByDate(list: ArrayList<Result>):ArrayList<Result> {
        list.sortByDescending { it.newest_published_date }
        return list
    }
    fun groupByUpdated(list: ArrayList<Result>):HashMap<String, ArrayList<Result>>{
        val grouped  = list.groupBy { it.updated }
        val groupedMap = HashMap<String, ArrayList<Result>>()
        groupedMap.put("WEEKLY", grouped.get("WEEKLY") as ArrayList<Result>)
        groupedMap.put("MONTHLY", grouped.get("MONTHLY") as ArrayList<Result>)
        return groupedMap
    }
}