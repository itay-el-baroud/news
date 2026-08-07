package com.newsme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reactions")
data class Reaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @SerializedName("news_id")
    val newsId: Int,
    
    @SerializedName("user_id")
    val userId: Int,
    
    @SerializedName("type")
    val type: String,
    
    @SerializedName("created_at")
    val createdAt: String = ""
)

data class ReactionCount(
    val newsId: Int,
    val likeCount: Int = 0,
    val angryCount: Int = 0,
    val sadCount: Int = 0
)
