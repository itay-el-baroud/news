package com.newsme.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.newsme.app.data.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    suspend fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE news_id = :newsId ORDER BY created_at DESC")
    fun getCommentsByNewsId(newsId: Int): Flow<List<Comment>>

    @Query("DELETE FROM comments")
    suspend fun clearComments()
}
