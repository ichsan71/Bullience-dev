package com.marqumil.bullience_dev.ui.screen.detail.detailNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marqumil.bullience_dev.data.NewsRepository
import com.marqumil.bullience_dev.model.News
import com.marqumil.bullience_dev.model.TotalNews
import com.marqumil.bullience_dev.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailNewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<TotalNews>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<TotalNews>>
        get() = _uiState

    fun getNewsById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getTotalNewsById(rewardId))
        }
    }

    fun addToCart(news: News, count: Int) {
        viewModelScope.launch {
            repository.updateNews(news.id, count)
        }
    }
}