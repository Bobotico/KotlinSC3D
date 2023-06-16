package com.example.smartcity3dar.ui.viewModels

import android.view.contentcapture.ContentCaptureSessionId
import androidx.lifecycle.ViewModel
import com.example.smartcity3dar.ui.models.SyncronizationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {

    val synchronizationModule = SyncronizationModule.getInstance()

    fun loadProjects(sessionId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val loginResult = synchronizationModule.getProjectList()
            // Process the loginResult here
            println("Lista progetti caricata :  " + loginResult?.SessionID)
        }
    }

}