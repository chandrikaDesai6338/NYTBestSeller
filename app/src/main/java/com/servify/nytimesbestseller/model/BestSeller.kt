package com.servify.nytimesbestseller.model

data class BestSeller(val status: String,
                      val copyright: String,
                      val num_results: Long,
                      val results: ArrayList<Result>)
