package com.zedan.newsapp.data.entities
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseTopHeadLines(
    @Json(name = "articles")
    val articles: List<Article>?,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "message")
    val message : String?,
    @Json(name = "code")
    val code : String?
) {
    @JsonClass(generateAdapter = true)
    data class Article(
        @Json(name = "author")
        val author: String?,
        @Json(name = "content")
        val content: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "publishedAt")
        val publishedAt: String?,
        @Json(name = "source")
        val source: Source?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "urlToImage")
        val urlToImage: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Source(
            @Json(name = "id")
            val id: String?,
            @Json(name = "name")
            val name: String?
        )
    }
}