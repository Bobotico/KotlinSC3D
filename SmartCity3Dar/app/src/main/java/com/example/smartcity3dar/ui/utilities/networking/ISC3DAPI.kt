package com.example.smartcity3dar.ui.utilities.networking

import com.example.smartcity3dar.ui.models.ProjectModel
import com.example.smartcity3dar.ui.models.SessionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ISC3DAPI {
        @POST("access/login")
        fun login(@Query("email") email:String, @Query("password") password:String): Call<SessionModel>

        @GET("projects/get")
        fun getProjects(@Header("x-SessionId") sessionId : String) : Call<ProjectModel>
}