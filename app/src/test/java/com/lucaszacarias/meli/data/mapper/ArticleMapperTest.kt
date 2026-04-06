package com.lucaszacarias.meli.data.mapper

import com.lucaszacarias.meli.data.remote.model.ArticleDto
import com.lucaszacarias.meli.data.remote.model.AuthorDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.TimeZone

class ArticleMapperTest {

    @Before
    fun setup() {
        // Fix TimeZone for tests to avoid local variation failures
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun `toDomain should map ArticleDto to Article correctly`() {
        // Given
        val dto = ArticleDto(
            id = 1,
            title = "Test Title",
            url = "https://example.com",
            imageUrl = "https://example.com/image.jpg",
            newsSite = "SpaceNews",
            summary = "Test Summary",
            publishedAt = "2024-05-20T10:00:00Z",
            updatedAt = "2024-05-21T11:30:00Z",
            featured = false,
            authors = listOf(AuthorDto("John Doe"))
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals(dto.id, domain.id)
        assertEquals(dto.title, domain.title)
        assertEquals("20/05/2024 10:00", domain.publishedAt)
        assertEquals("21/05/2024 11:30", domain.updatedAt)
        assertEquals(listOf("John Doe"), domain.authors)
    }

    @Test
    fun `toDomain should handle invalid date format gracefully`() {
        // Given
        val dto = ArticleDto(
            id = 1,
            title = "Test Title",
            url = "https://example.com",
            imageUrl = "https://example.com/image.jpg",
            newsSite = "SpaceNews",
            summary = "Test Summary",
            publishedAt = "invalid-date",
            updatedAt = "2024-05-21T11:30:00Z",
            featured = false,
            authors = emptyList()
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals("invalid-date", domain.publishedAt)
        assertEquals("21/05/2024 11:30", domain.updatedAt)
    }
}
