package com.example.smartcity3dar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartcity3dar.models.SessionModel
import com.example.smartcity3dar.models.SyncronizationModule
import com.example.smartcity3dar.repository.MainRepository
import com.example.smartcity3dar.services.DataCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: MainRepository,
    val dataCache: DataCache
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    suspend fun login(username: String, password : String) : SessionModel =
        withContext(Dispatchers.Default) {
            var loginResult = repository.login(username, password)
            return@withContext loginResult!!
        }
}