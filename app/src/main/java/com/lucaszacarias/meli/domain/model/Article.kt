package com.lucaszacarias.meli.domain.model

data class Article(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val authors: List<String> = emptyList()
)
