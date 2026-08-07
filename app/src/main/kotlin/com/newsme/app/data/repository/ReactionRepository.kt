package com.newsme.app.data.repository

import com.newsme.app.data.local.ReactionDao
import com.newsme.app.data.remote.ApiService
import com.newsme.app.data.model.Reaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReactionRepository @Inject constructor(
    private val apiService: ApiService,
    private val reactionDao: ReactionDao
) {
    fun getReactionsByNewsId(newsId: Int): Flow<List<Reaction>> =
        reactionDao.getReactionsByNewsId(newsId)

    suspend fun addReaction(reaction: Reaction, token: String): Result<Reaction> = try {
        val response = apiService.addReaction(reaction, "Bearer $token")
        if (response.success) {
            response.data?.let { reactionDao.insertReaction(it) }
            Result.success(response.data!!)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deleteReaction(newsId: Int, userId: Int, token: String): Result<String> = try {
        val reactionId = reactionDao.getUserReaction(newsId, userId)?.id ?: return Result.failure(
            Exception("Reaction not found")
        )
        val response = apiService.deleteReaction(reactionId, "Bearer $token")
        if (response.success) {
            reactionDao.deleteReaction(newsId, userId)
            Result.success(response.message)
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
