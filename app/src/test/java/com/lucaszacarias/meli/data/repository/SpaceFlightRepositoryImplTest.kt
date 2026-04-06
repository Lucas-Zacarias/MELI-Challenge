package com.lucaszacarias.meli.data.repository

import com.lucaszacarias.meli.data.remote.api.SpaceFlightApi
import com.lucaszacarias.meli.data.remote.model.ArticleDto
import com.lucaszacarias.meli.data.remote.model.NewsResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SpaceFlightRepositoryImplTest {

    private val api: SpaceFlightApi = mockk()
    private val repository = SpaceFlightRepositoryImpl(api)

    @Test
    fun `getLatestArticles should return list of articles from api`() = runTest {
        // Given
        val apiResponse = NewsResponseDto(
            count = 1,
            next = null,
            previous = null,
            results = listOf(
                ArticleDto(
                    id = 1,
                    title = "Test",
                    url = "",
                    imageUrl = "",
                    newsSite = "",
                    summary = "",
                    publishedAt = "2024-05-20T10:00:00Z",
                    updatedAt = "2024-05-20T10:00:00Z",
                    featured = false,
                    authors = emptyList()
                )
            )
        )
        coEvery { api.getLatestArticles() } returns apiResponse

        // When
        val result = repository.getLatestArticles()

        // Then
        assertEquals(1, result.size)
        assertEquals("Test", result[0].title)
    }

    @Test
    fun `getArticleDetail should return article detail from api`() = runTest {
        // Given
        val dto = ArticleDto(
            id = 1,
            title = "Detail Test",
            url = "",
            imageUrl = "",
            newsSite = "",
            summary = "",
            publishedAt = "2024-05-20T10:00:00Z",
            updatedAt = "2024-05-20T10:00:00Z",
            featured = false,
            authors = emptyList()
        )
        coEvery { api.getArticleDetail(1) } returns dto

        // When
        val result = repository.getArticleDetail(1)

        // Then
        assertEquals("Detail Test", result.title)
    }
}
