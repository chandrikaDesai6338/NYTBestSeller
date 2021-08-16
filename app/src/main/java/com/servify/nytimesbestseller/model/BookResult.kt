package com.servify.nytimesbestseller.model

data class BookResult(
    val list_name: String,
    val display_name: String,
    val bestsellers_date: String,
    val published_date: String,
    val rank: Long,
    val rank_last_week: Long,
    val weeks_on_list: Long,
    val asterisk: Long,
    val dagger: Long,
    val amazon_product_url: String,
    val isbns: ArrayList<Isbn>,
    val book_details: ArrayList<BookDetail>,
    val reviews: ArrayList<Review>
)

data class BookDetail(
    val title: String,
    val description: String,
    val contributor: String,
    val author: String,
    val contributor_note: String,
    val price: Long,
    val age_group: String,
    val publisher: String,
    val primary_isbn13: String,
    val primary_isbn10: String
)

data class Isbn(
    val isbn10: String,
    val isbn13: String
)

data class Review(
    val book_review_link: String,
    val first_chapter_link: String,
    val sunday_review_link: String,
    val article_chapter_link: String
)
