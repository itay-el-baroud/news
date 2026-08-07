package com.newsme.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsme.app.data.model.News
import com.newsme.app.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsList: StateFlow<List<News>> get() = newsRepository.getAllNews()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _newsAddedEvent = MutableSharedFlow<Boolean>()
    val newsAddedEvent = _newsAddedEvent

    fun fetchNews() {
        viewModelScope.launch {
            _isLoading.value = true
            newsRepository.fetchAndCacheNews()
                .onSuccess {
                    _isLoading.value = false
                }
                .onFailure {
                    _isLoading.value = false
                }
        }
    }

    fun addNews(title: String, content: String, link: String, isPrivate: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            val news = News(
                title = title,
                content = content,
                link = link,
                isPrivate = isPrivate,
                authorId = 1,
                authorName = "Current User"
            )
            newsRepository.createNews(news, "dummy_token")
                .onSuccess {
                    _isLoading.value = false
                    _newsAddedEvent.emit(true)
                }
                .onFailure {
                    _isLoading.value = false
                }
        }
    }
}
