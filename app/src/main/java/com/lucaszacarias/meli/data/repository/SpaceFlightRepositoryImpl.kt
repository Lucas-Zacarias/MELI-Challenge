package com.lucaszacarias.meli.data.repository

import com.lucaszacarias.meli.data.mapper.toDomain
import com.lucaszacarias.meli.data.remote.api.SpaceFlightApi
import com.lucaszacarias.meli.domain.model.Article
import com.lucaszacarias.meli.domain.repository.SpaceFlightRepository

class SpaceFlightRepositoryImpl(
    private val api: SpaceFlightApi
) : SpaceFlightRepository {
    override suspend fun getLatestArticles(): List<Article> {
        val response = api.getLatestArticles()
        val articles = response.results.map { it.toDomain() }
        return articles
    }

    override suspend fun searchArticles(query: String): List<Article> {
        val response = api.searchArticles(query)
        val articles = response.results.map { it.toDomain() }
        return articles
    }

    override suspend fun getArticleDetail(id: Int): Article {
        return api.getArticleDetail(id).toDomain()
    }
}