package com.newsme.app.data.repository

import com.newsme.app.data.local.UserDao
import com.newsme.app.data.remote.ApiService
import com.newsme.app.data.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun sendOTP(email: String): Result<String> = try {
        val response = apiService.sendOTP(email)
        if (response.success) Result.success(response.message)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun verifyOTP(email: String, otp: String): Result<String> = try {
        val response = apiService.verifyOTP(email, otp)
        if (response.success) Result.success(response.token ?: "")
        else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun register(username: String, email: String, password: String): Result<String> = try {
        val response = apiService.register(username, email, password)
        if (response.success) {
            response.data?.let { userDao.insertUser(it) }
            Result.success(response.token ?: "")
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun login(email: String, password: String): Result<String> = try {
        val response = apiService.login(email, password)
        if (response.success) {
            response.data?.let { userDao.insertUser(it) }
            Result.success(response.token ?: "")
        } else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun forgotPassword(email: String): Result<String> = try {
        val response = apiService.forgotPassword(email)
        if (response.success) Result.success(response.message)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
