package com.example.smartcity3dar.repository

import com.example.smartcity3dar.models.AssetModel
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.models.SessionModel
import com.example.smartcity3dar.networking.ISC3DAPI
import com.example.smartcity3dar.services.DataCache
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    val sc3dAPI : ISC3DAPI,
    val dataCache: DataCache
):MainRepository {
    override suspend fun login(username: String, password: String): SessionModel? = withContext(Dispatchers.IO){
        var loginResult: SessionModel? = null

        try {
            val response = sc3dAPI?.login(username, password)?.awaitResponse()
            if (response != null && response.isSuccessful) {
                println("Response " + Gson().toJson(response.body()))
                loginResult = response.body()
                dataCache.currentSession = response.body()
            } else {
                println("Response fail : " + response?.code())
            }
        } catch (t: Throwable) {
            println("Error: " + t.message)
        }

        loginResult
    }

    override suspend fun getAssets(sessionID: String): AssetModel? = withContext(Dispatchers.IO) {
        var assets: AssetModel? = null

        try {
            if(dataCache.currentSession != null){
                sc3dAPI?.getAssets(sessionID, 1)?.enqueue(object  : Callback<AssetModel> {
                    override fun onResponse(
                        call: Call<AssetModel>,
                        response: Response<AssetModel>
                    ) {
                        if(response.isSuccessful){
                            assets = response.body()
                            dataCache.assetsLoaded = response.body()
                            response.body().let {
                                if (it != null) {
                                    for (asset in it.Data) {
                                        println(asset.Name)
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<AssetModel>, t: Throwable) {
                        println("Errore lista asset : " + t.message)
                    }

                })
            }
        }catch (e : Exception) {

        }
        assets
    }

    override suspend fun getProjectList(): ProjectModel? = withContext(Dispatchers.IO) {

        var result: ProjectModel? = null
        try {
            if (dataCache.currentSession != null) {
                val response =
                    sc3dAPI?.getProjects(dataCache.currentSession!!.SessionID)?.awaitResponse()
                if (response != null && response.isSuccessful) {
                    result = response.body()
                    dataCache.projects = response.body()
                } else {
                    println("Errore nel caricamento dei progetti : ${response?.code()}")
                }
            }
        } catch (e: Exception) {
            println("Eccezzione : ${e.message}")
        }
        result
    }
}