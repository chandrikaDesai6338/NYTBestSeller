package com.servify.nytimesbestseller.model


data class Result(val list_name: String,
                  val display_name: String,
                  val list_name_encoded: String,
                  val oldest_published_date: String,
                  val newest_published_date: String,
                  val updated: String)
