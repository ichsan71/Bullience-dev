package com.marqumil.bullience_dev.ui.screen.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch as viewModelLaunch
import com.kitafitapp.kitafit.data.remote.ResultState
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.data.local.SharedPrefs.Companion.KEY_LOGIN
import com.marqumil.bullience_dev.data.network.ApiConfig
import com.marqumil.bullience_dev.data.remote.post.LoginBody
import com.marqumil.bullience_dev.data.remote.response.LoginResponse
import com.marqumil.bullience_dev.ui.common.UiState
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SignInViewModel(
//    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<LoginResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<LoginResponse>>
        get() = _uiState

    fun pushLogin(email: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val loginBody = LoginBody(email, password)
            try {
                Log.d("Login", "Try")
                val apiClient = ApiConfig.getApiService().postLogin(loginBody)
                val data = apiClient

                if (data.token != null) {
                    Hawk.put(KEY_LOGIN, data.token)
                    _uiState.value = UiState.Success(data)
                    Log.d("Login", "Success")
                } else {
                    _uiState.value = UiState.Error(data.message ?: " username or password is wrong")
                    Log.d("Login", " ${data.message} username or password is wrong")
                }
            }
            catch (e : Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                Log.d("Login", " Error exception ${e.message}")
            }
        }
    }

//    fun getAllRewards() {
//        viewModelScope.launch {
//            repository.getAllCourses()
//                .catch {
//                    _uiState.value = UiState.Error(it.message.toString())
//                }
//                .collect { orderRewards ->
//                    _uiState.value = UiState.Success(orderRewards)
//                }
//        }
//    }
}

//fun login(nik: String, password: String) {
//    if (nik == "" || nik.isEmpty()) {
//        _nikError.value = "Silakan masukkan NIK terlebih dahulu!"
//    } else if (password == "" || password.isEmpty()) {
//        _passwordError.value = "Silakan masukkan password terlebih dahulu!"
//    } else {
//        pushLogin(nik, password)
//    }
//}

//private fun pushLogin(nik: String, password: String) {
//    _loginState.value = ResultState.Loading
//    viewModelScope.launch(Dispatchers.IO) {
//        val loginBody = LoginBody(nik, password)
//        try {
//            val apiClient = ApiConfig.getApiService().postLogin(loginBody)
//            val data = apiClient.data
//            if (data != null) {
//                Hawk.put(KEY_LOGIN, data)
//                _loginState.postValue(ResultState.Success(true))
//            }
//            else {
//                _loginState.postValue(ResultState.Failure(Exception(apiClient.message ?: "Unknown Error")))
//            }
//        }
//        catch (e : Exception) {
//            _loginState.postValue(ResultState.Failure(e))
//        }
//    }
//}
//
//fun getUser() {
//    viewModelScope.launch {
//        try {
//            _userState.value = ResultState.Loading
//            val response = email?.let { ApiConfig.getApiService().getUser(it) }
//            val user = response?.data
//            if (user != null) {
//                _userState.postValue(ResultState.Success(user))
//            } else {
//                _userState.postValue(ResultState.Failure(Throwable("Data Kosong")))
//            }
//        } catch (e: Exception) {
//            _userState.postValue(ResultState.Failure(e))
//        }
//    }
//}