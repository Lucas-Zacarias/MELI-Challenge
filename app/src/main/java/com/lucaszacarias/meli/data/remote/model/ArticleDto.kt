package com.lucaszacarias.meli.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("news_site") val newsSite: String,
    @SerialName("summary") val summary: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("featured") val featured: Boolean,
    @SerialName("authors") val authors: List<AuthorDto> = emptyList()
)

@Serializable
data class AuthorDto(
    @SerialName("name") val name: String
)
