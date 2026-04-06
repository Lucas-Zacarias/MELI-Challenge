package com.lucaszacarias.meli.ui.mainview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaszacarias.meli.domain.model.Article
import com.lucaszacarias.meli.domain.repository.SpaceFlightRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class MainViewModel(
    private val repository: SpaceFlightRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchUiState> = _searchQuery
        .flatMapLatest { query ->
            flow {
                emit(SearchUiState.Loading)
                try {
                    val results = if (query.isEmpty()) {
                        repository.getLatestArticles()
                    } else {
                        repository.searchArticles(query)
                    }

                    if (results.isEmpty()) emit(SearchUiState.Empty)
                    else emit(SearchUiState.Success(results))
                } catch (e: Exception) {
                    Timber.e(e)
                    emit(SearchUiState.Error)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Loading
        )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Success(val articles: List<Article>) : SearchUiState
    object Error : SearchUiState
    object Empty : SearchUiState
}