package com.example.smartcity3dar.repository

import com.example.smartcity3dar.models.AssetModel
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.models.SessionModel

interface MainRepository {

    suspend fun login(username: String, password: String): SessionModel?

    suspend fun getAssets(sessionID : String) : AssetModel?

    suspend fun getProjectList() : ProjectModel?
}