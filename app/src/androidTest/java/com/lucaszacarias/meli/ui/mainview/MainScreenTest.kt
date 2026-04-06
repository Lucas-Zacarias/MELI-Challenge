package com.lucaszacarias.meli.ui.mainview

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.lucaszacarias.meli.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreen_typingInSearch_updatesViewModel() {
        // Given
        val uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
        val viewModel: MainViewModel = mockk(relaxed = true)
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            MainScreen(viewModel = viewModel, onNavigateToDetail = {})
        }

        // When
        val searchText = "Mars Rover"
        composeTestRule.onNodeWithText("Buscar noticias…").performTextInput(searchText)

        // Then
        verify { viewModel.onSearchQueryChanged(searchText) }
    }

    @Test
    fun mainScreen_successState_showsArticles() {
        // Given
        val articles = listOf(
            Article(
                id = 1,
                title = "SpaceX Launch",
                url = "url",
                imageUrl = "image",
                newsSite = "SpaceNews",
                summary = "Summary",
                publishedAt = "date",
                updatedAt = "update",
                featured = false,
                authors = emptyList()
            )
        )
        val uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(articles))
        val viewModel: MainViewModel = mockk()
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            MainScreen(viewModel = viewModel, onNavigateToDetail = {})
        }

        // Then
        composeTestRule.onNodeWithText("SpaceX Launch").assertIsDisplayed()
        composeTestRule.onNodeWithText("SpaceNews").assertIsDisplayed()
    }

    @Test
    fun mainScreen_errorState_showsErrorMessage() {
        // Given
        val uiState = MutableStateFlow<SearchUiState>(SearchUiState.Error)
        val viewModel: MainViewModel = mockk()
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            MainScreen(viewModel = viewModel, onNavigateToDetail = {})
        }

        // Then
        composeTestRule.onNodeWithText("Error al cargar las noticias").assertIsDisplayed()
    }
}
