package com.newsme.app.data.remote

import com.newsme.app.data.model.User
import com.newsme.app.data.model.News
import com.newsme.app.data.model.Reaction
import com.newsme.app.data.model.Comment
import retrofit2.http.*

interface ApiService {
    
    companion object {
        const val BASE_URL = "https://cccjvhhhlppkbev.rf.gd/"
    }

    @POST("send_email.php")
    @FormUrlEncoded
    suspend fun sendOTP(@Field("email") email: String): ApiResponse<String>

    @POST("verification.php")
    @FormUrlEncoded
    suspend fun verifyOTP(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): ApiResponse<TokenResponse>

    @POST("register.php")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<TokenResponse>

    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<TokenResponse>

    @POST("forgot_password.php")
    @FormUrlEncoded
    suspend fun forgotPassword(@Field("email") email: String): ApiResponse<String>

    @GET("news/")
    suspend fun getAllNews(
        @Query("page") page: Int = 1
    ): ApiResponse<List<News>>

    @POST("news/")
    suspend fun createNews(
        @Body news: News,
        @Header("Authorization") token: String
    ): ApiResponse<News>

    @GET("news/{id}")
    suspend fun getNewsById(@Path("id") newsId: Int): ApiResponse<News>

    @DELETE("news/{id}")
    suspend fun deleteNews(
        @Path("id") newsId: Int,
        @Header("Authorization") token: String
    ): ApiResponse<String>

    @POST("reactions/")
    suspend fun addReaction(
        @Body reaction: Reaction,
        @Header("Authorization") token: String
    ): ApiResponse<Reaction>

    @GET("news/{id}/reactions")
    suspend fun getReactions(@Path("id") newsId: Int): ApiResponse<List<Reaction>>

    @DELETE("reactions/{id}")
    suspend fun deleteReaction(
        @Path("id") reactionId: Int,
        @Header("Authorization") token: String
    ): ApiResponse<String>

    @POST("comments/")
    suspend fun addComment(
        @Body comment: Comment,
        @Header("Authorization") token: String
    ): ApiResponse<Comment>

    @GET("news/{id}/comments")
    suspend fun getComments(@Path("id") newsId: Int): ApiResponse<List<Comment>>
}

data class ApiResponse<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null,
    val token: String? = null
)

data class TokenResponse(
    val token: String,
    val user: User? = null
)
