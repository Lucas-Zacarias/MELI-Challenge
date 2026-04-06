package com.lucaszacarias.meli.data.remote.api

import com.lucaszacarias.meli.data.remote.model.ArticleDto
import com.lucaszacarias.meli.data.remote.model.NewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SpaceFlightApiImpl(private val client: HttpClient) : SpaceFlightApi {
    override suspend fun getLatestArticles(limit: Int): NewsResponseDto =
        client.get("articles") {
            parameter("limit", limit)
            parameter("ordering", "-published_at")
        }.body()

    override suspend fun searchArticles(query: String): NewsResponseDto {
        return client.get("articles/") {
            parameter("search", query)
        }.body()
    }

    override suspend fun getArticleDetail(id: Int): ArticleDto {
        return client.get("articles/$id/").body()
    }
}
