package com.servify.nytimesbestseller.model

data class BestSellerBookList(val status: String,
                              val copyright: String,
                              val num_results: Long,
                              val last_modified: String,
                              val results: ArrayList<BookResult>)
