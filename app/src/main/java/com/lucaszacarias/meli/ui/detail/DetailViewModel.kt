package com.lucaszacarias.meli.ui.detail

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

class DetailViewModel(
    private val repository: SpaceFlightRepository
) : ViewModel() {

    private val _articleId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DetailUiState> = _articleId
        .flatMapLatest { id ->
            flow {
                if (id == null) return@flow
                emit(DetailUiState.Loading)
                try {
                    val article = repository.getArticleDetail(id)
                    emit(DetailUiState.Success(article))
                } catch (e: Exception) {
                    Timber.e(e)
                    emit(DetailUiState.Error)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState.Loading
        )

    fun loadArticleDetail(id: Int) {
        if (_articleId.value == id) return
        _articleId.value = id
    }
}

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val article: Article) : DetailUiState
    object Error : DetailUiState
}
