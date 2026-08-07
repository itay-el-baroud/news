package com.newsme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @SerializedName("news_id")
    val newsId: Int,
    
    @SerializedName("user_id")
    val userId: Int,
    
    @SerializedName("username")
    val username: String = "",
    
    @SerializedName("content")
    val content: String,
    
    @SerializedName("created_at")
    val createdAt: String = ""
)
