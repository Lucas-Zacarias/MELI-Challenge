package com.lucaszacarias.meli.ui.mainview

import app.cash.turbine.test
import com.lucaszacarias.meli.domain.model.Article
import com.lucaszacarias.meli.domain.repository.SpaceFlightRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val repository: SpaceFlightRepository = mockk()
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem() is SearchUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `success loading articles should emit Success state`() = runTest {
        // Given
        val articles = listOf(
            Article(1, "Title", "url", "image", "site", "summary", "date", "update", false, emptyList())
        )
        coEvery { repository.getLatestArticles() } returns articles

        // When
        viewModel.onSearchQueryChanged("") // Trigger flow

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem() is SearchUiState.Loading)
            val state = awaitItem()
            assertTrue(state is SearchUiState.Success)
            assertEquals(articles, (state as SearchUiState.Success).articles)
        }
    }

    @Test
    fun `error loading articles should emit Error state`() = runTest {
        // Given
        coEvery { repository.getLatestArticles() } throws Exception("Network error")

        // When
        viewModel.onSearchQueryChanged("")

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem() is SearchUiState.Loading)
            assertTrue(awaitItem() is SearchUiState.Error)
        }
    }
}

// Extension function to help with assertEquals if not imported
private fun assertEquals(expected: Any?, actual: Any?) {
    org.junit.Assert.assertEquals(expected, actual)
}
