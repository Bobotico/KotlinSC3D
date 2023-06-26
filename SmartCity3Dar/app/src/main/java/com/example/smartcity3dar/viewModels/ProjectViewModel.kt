package com.example.smartcity3dar.viewModels

import android.view.contentcapture.ContentCaptureSessionId
import androidx.lifecycle.ViewModel
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.models.SyncronizationModule
import com.example.smartcity3dar.repository.MainRepository
import com.example.smartcity3dar.services.DataCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    val repository: MainRepository
) : ViewModel() {

    val synchronizationModule = SyncronizationModule.getInstance()

    suspend fun loadProjects() : ProjectModel =
        withContext(Dispatchers.Default) {
            val projectResult = repository.getProjectList()
            return@withContext projectResult!!
    }
}