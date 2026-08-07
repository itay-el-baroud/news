package com.newsme.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.newsme.app.data.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert
    suspend fun insertNews(news: News)

    @Update
    suspend fun updateNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("SELECT * FROM news ORDER BY created_at DESC")
    fun getAllNews(): Flow<List<News>>

    @Query("SELECT * FROM news WHERE id = :newsId")
    suspend fun getNewsById(newsId: Int): News?

    @Query("DELETE FROM news")
    suspend fun clearNews()
}
