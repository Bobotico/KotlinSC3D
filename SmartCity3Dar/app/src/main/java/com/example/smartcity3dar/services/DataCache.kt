package com.example.smartcity3dar.services

import com.example.smartcity3dar.models.AssetModel
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.models.SessionModel

class DataCache {

    var currentSession : SessionModel? = null

    var projects : ProjectModel? = null

    var assetsLoaded : AssetModel? = null

}