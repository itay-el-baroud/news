package com.newsme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String? = null,
    
    @SerializedName("token")
    val token: String? = null,
    
    val location: String = "",
    val darkMode: Boolean = false,
    val language: String = "ar"
)
