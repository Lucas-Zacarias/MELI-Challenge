package com.lucaszacarias.meli.data.mapper

import com.lucaszacarias.meli.data.remote.model.ArticleDto
import com.lucaszacarias.meli.domain.model.Article
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = formatIsoDate(publishedAt),
        updatedAt = formatIsoDate(updatedAt),
        featured = featured,
        authors = authors.map { it.name }
    )
}

private fun formatIsoDate(isoDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        
        val date = inputFormat.parse(isoDate)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        date?.let { outputFormat.format(it) } ?: isoDate
    } catch (_: Exception) {
        isoDate
    }
}
