package com.example.smartcity3dar.networking

import com.example.smartcity3dar.models.AssetModel
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.models.SessionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ISC3DAPI {
        @POST("access/login")
        fun login(@Query("email") email:String, @Query("password") password:String): Call<SessionModel>

        @GET("projects/get")
        fun getProjects(@Header("x-SessionId") sessionId : String) : Call<ProjectModel>

        @GET("project/{projectID}/assets/get")
        fun getAssets(@Header("x-SessionId") sessionId: String, @Path("projectID") projectID: Int): Call<AssetModel>
}