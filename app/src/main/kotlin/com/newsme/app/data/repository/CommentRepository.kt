package com.newsme.app.data.repository

import com.newsme.app.data.local.CommentDao
import com.newsme.app.data.remote.ApiService
import com.newsme.app.data.model.Comment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val apiService: ApiService,
    private val commentDao: CommentDao
) {
    fun getCommentsByNewsId(newsId: Int): Flow<List<Comment>> =
        commentDao.getCommentsByNewsId(newsId)

    suspend fun addComment(comment: Comment, token: String): Result<Comment> = try {
        val response = apiService.addComment(comment, "Bearer $token")
        if (response.success) {
            response.data?.let { commentDao.insertComment(it) }
            Result.success(response.data!!)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun fetchAndCacheComments(newsId: Int): Result<Unit> = try {
        val response = apiService.getComments(newsId)
        if (response.success) {
            response.data?.forEach { commentDao.insertComment(it) }
            Result.success(Unit)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
