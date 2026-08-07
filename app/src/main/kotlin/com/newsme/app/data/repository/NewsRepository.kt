package com.newsme.app.data.repository

import com.newsme.app.data.local.NewsDao
import com.newsme.app.data.remote.ApiService
import com.newsme.app.data.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {
    fun getAllNews(): Flow<List<News>> = newsDao.getAllNews()

    suspend fun fetchAndCacheNews(): Result<Unit> = try {
        val response = apiService.getAllNews()
        if (response.success) {
            newsDao.clearNews()
            response.data?.forEach { newsDao.insertNews(it) }
            Result.success(Unit)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getNewsById(newsId: Int): Result<News> = try {
        val response = apiService.getNewsById(newsId)
        if (response.success) Result.success(response.data!!)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun createNews(news: News, token: String): Result<News> = try {
        val response = apiService.createNews(news, "Bearer $token")
        if (response.success) {
            response.data?.let { newsDao.insertNews(it) }
            Result.success(response.data!!)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deleteNews(newsId: Int, token: String): Result<String> = try {
        val response = apiService.deleteNews(newsId, "Bearer $token")
        if (response.success) {
            Result.success(response.message)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
