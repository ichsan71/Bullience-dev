package com.marqumil.bullience_dev.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marqumil.bullience_dev.data.NewsRepository
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.data.network.ApiConfig
import com.marqumil.bullience_dev.data.remote.ResponseObject
import com.marqumil.bullience_dev.data.remote.response.UserResponse
import com.marqumil.bullience_dev.model.TotalNews
import com.marqumil.bullience_dev.ui.common.UiState
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ResponseObject<UserResponse>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ResponseObject<UserResponse>>>
        get() = _uiState

    private val _uiStateNews: MutableStateFlow<UiState<List<TotalNews>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateNews: StateFlow<UiState<List<TotalNews>>>
        get() = _uiStateNews

    fun getUser() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("UserState vm", "Try")
            try {
                val token = Hawk.get<String>(SharedPrefs.KEY_LOGIN)
                val apiClient = ApiConfig.getApiService().getUserWithToken(
                    "Bearer $token"
                )
                val data = apiClient.user
                Log.d("UserState vm", "$data")
                if (data != null) {
                    Log.d("UserState vm", "Success")
                    Hawk.put(SharedPrefs.EMAIL, data.email)
                    _uiState.value = UiState.Success(apiClient)
                } else {
                    Log.d("UserState vm", "Error tanpa msg")
                    _uiState.value = UiState.Error(
                        apiClient.message ?: " Error")
                }
            } catch (e: Exception) {
                Log.d("UserState vm", "Error exception ${e.message}")
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getAllNews() {
        viewModelScope.launch {
            repository.getAllNews()
                .catch {
                    _uiStateNews.value = UiState.Error(it.message.toString())
                }
                .collect { totalNews ->
                    _uiStateNews.value = UiState.Success(totalNews)
                }
        }
    }
}
