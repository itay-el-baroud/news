package com.newsme.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsme.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val token: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthUiState())
    val authState: StateFlow<AuthUiState> = _authState

    fun sendOTP(email: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.sendOTP(email)
            result.onSuccess {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = null
                )
            }.onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Unknown error"
                )
            }
        }
    }

    fun verifyOTP(email: String, otp: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.verifyOTP(email, otp)
            result.onSuccess { token ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    token = token
                )
            }.onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Unknown error"
                )
            }
        }
    }

    fun register(username: String, email: String, password: String, otp: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.register(username, email, password)
            result.onSuccess { token ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    token = token
                )
            }.onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Unknown error"
                )
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.login(email, password)
            result.onSuccess { token ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    token = token
                )
            }.onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Unknown error"
                )
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.forgotPassword(email)
            result.onSuccess {
                _authState.value = _authState.value.copy(isLoading = false)
            }.onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Unknown error"
                )
            }
        }
    }
}
