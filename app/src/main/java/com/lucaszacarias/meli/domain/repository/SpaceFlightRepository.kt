package com.lucaszacarias.meli.domain.repository

import com.lucaszacarias.meli.domain.model.Article

interface SpaceFlightRepository {
    suspend fun getLatestArticles(): List<Article>
    suspend fun searchArticles(query: String): List<Article>
    suspend fun getArticleDetail(id: Int): Article
}