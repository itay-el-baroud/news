package com.newsme.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.newsme.app.data.model.Reaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ReactionDao {
    @Insert
    suspend fun insertReaction(reaction: Reaction)

    @Query("SELECT * FROM reactions WHERE news_id = :newsId")
    fun getReactionsByNewsId(newsId: Int): Flow<List<Reaction>>

    @Query("SELECT * FROM reactions WHERE news_id = :newsId AND user_id = :userId LIMIT 1")
    suspend fun getUserReaction(newsId: Int, userId: Int): Reaction?

    @Query("DELETE FROM reactions WHERE news_id = :newsId AND user_id = :userId")
    suspend fun deleteReaction(newsId: Int, userId: Int)

    @Query("DELETE FROM reactions")
    suspend fun clearReactions()
}
