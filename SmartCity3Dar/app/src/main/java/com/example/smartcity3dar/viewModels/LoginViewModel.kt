package com.example.smartcity3dar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartcity3dar.models.SyncronizationModule
import com.example.smartcity3dar.repository.MainRepository
import com.example.smartcity3dar.services.DataCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun login(username: String, password : String) {
        CoroutineScope(Dispatchers.Main).launch {
            val loginResult = repository.login(username, password)
            // Process the loginResult here
            println("Accesso effettuato con id : " + dataCache.currentSession?.SessionID)
        }
    }
}