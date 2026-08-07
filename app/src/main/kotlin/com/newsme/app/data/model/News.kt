package com.newsme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("content")
    val content: String,
    
    @SerializedName("author_id")
    val authorId: Int,
    
    @SerializedName("author_name")
    val authorName: String = "",
    
    @SerializedName("image_url")
    val imageUrl: String? = null,
    
    @SerializedName("video_url")
    val videoUrl: String? = null,
    
    @SerializedName("link")
    val link: String? = null,
    
    @SerializedName("location")
    val location: String = "",
    
    @SerializedName("is_private")
    val isPrivate: Boolean = false,
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("updated_at")
    val updatedAt: String = ""
)
