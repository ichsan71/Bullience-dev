package com.marqumil.bullience_dev.ui.screen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marqumil.bullience_dev.data.network.ApiConfig
import com.marqumil.bullience_dev.data.remote.post.SignUpBody
import com.marqumil.bullience_dev.data.remote.response.SignUpResponse
import com.marqumil.bullience_dev.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
//    private val repository: NewsRepository
) : ViewModel() {
//    private val _uiState: MutableStateFlow<UiState<List<OrderCourse>>> = MutableStateFlow(UiState.Loading)
//    val uiState: StateFlow<UiState<List<OrderCourse>>>
//        get() = _uiState

//    private val token = Hawk.get<LoginResponse?>(SharedPrefs.KEY_LOGIN)?.token

    private val _uiState: MutableStateFlow<UiState<SignUpResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<SignUpResponse>>
        get() = _uiState

    fun pushRegister(
        username: String,
        email: String,
        password: String,
        confirmasiPassword: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val signUpBody =  SignUpBody(username, email, password, confirmasiPassword)

            try {
                val apiClient = ApiConfig.getApiService().postRegister(signUpBody)
                Log.d("RegisterVM", " TRY : ${apiClient.message} ")
                if (apiClient.message == "Akun berhasil didaftarkan") {
                    _uiState.value = UiState.Success(apiClient)
                    Log.d("RegisterVM", "Success1")
                } else {
                    _uiState.value = UiState.Error(apiClient.message ?: " Error")
                    Log.d("RegisterVM", " ${apiClient.message} Error")
                }

            } catch (e : Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                Log.d("RegisterVM", " Error exception ${e.message}")
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