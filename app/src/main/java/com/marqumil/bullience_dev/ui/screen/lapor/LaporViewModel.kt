package com.marqumil.bullience_dev.ui.screen.lapor


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.data.local.SharedPrefs.Companion.KEY_LOGIN
import com.marqumil.bullience_dev.data.network.ApiConfig
import com.marqumil.bullience_dev.data.remote.post.LoginBody
import com.marqumil.bullience_dev.data.remote.response.LoginResponse
import com.marqumil.bullience_dev.data.remote.response.ResponseLabelItem
import com.marqumil.bullience_dev.ui.common.UiState
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LaporViewModel(
//    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<List<ResponseLabelItem>>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<List<ResponseLabelItem>>>>
        get() = _uiState

    private val _labelItem = MutableLiveData<ResponseLabelItem>()
    val labelItem: LiveData<ResponseLabelItem> = _labelItem

    fun predict(
        inputs : String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                val apiClient = ApiConfig.getApiServiceIndobert().postInputs(inputs)
                val dataResponse = apiClient[0][0]
                val data = apiClient[0][0].label
                if (data != null) {
                    _uiState.value = UiState.Success(apiClient)
                    when (data) {
                        "LABEL_0" -> {
                            _labelItem.value = dataResponse
                        }
                        "LABEL_1" -> {
                            _labelItem.value = dataResponse
                        }
                        else -> {
                            _labelItem.value = dataResponse
                        }
                    }
                    Log.d("LaporVM", "Success1 $data")
                }
                else {
                    _uiState.value = UiState.Error(apiClient[0][0].score ?: "Unknown Error")
                }
            }
            catch (e : Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }


}

