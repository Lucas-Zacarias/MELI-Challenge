package com.lucaszacarias.meli.data.remote.api

import com.lucaszacarias.meli.data.remote.model.ArticleDto
import com.lucaszacarias.meli.data.remote.model.NewsResponseDto

interface SpaceFlightApi {
    suspend fun getLatestArticles(limit: Int = 10): NewsResponseDto
    suspend fun searchArticles(query: String): NewsResponseDto
    suspend fun getArticleDetail(id: Int): ArticleDto
}