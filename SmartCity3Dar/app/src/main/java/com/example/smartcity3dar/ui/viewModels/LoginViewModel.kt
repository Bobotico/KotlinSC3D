package com.example.smartcity3dar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartcity3dar.ui.models.SessionModel
import com.example.smartcity3dar.ui.models.SyncronizationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val synchronizationModule = SyncronizationModule()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun login(username: String, password : String) {
        CoroutineScope(Dispatchers.Main).launch {
            val loginResult = synchronizationModule.loginAsync(username, password)
            // Process the loginResult here
            println("Accesso effettuato con id : " + loginResult?.SessionID)
        }
    }
}