package com.example.smartcity3dar.models

import com.example.smartcity3dar.networking.ISC3DAPI
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class SyncronizationModule private constructor(){

    private var currentSession : SessionModel? = null
    private var projects : ProjectModel? = null

    companion object {

        @Volatile
        private var instance: SyncronizationModule? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SyncronizationModule().also { instance = it }
            }
    }

    var client : ISC3DAPI?
    init {
        client = Retrofit.Builder()
            .baseUrl("https://cloud17.smartcity3d.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ISC3DAPI::class.java)
    }

    /*fun login(username: String, password: String) : SessionModel? {
        var loginResult: SessionModel? = null

        client?.login("TEst", "Test")
            ?.enqueue(object : Callback<SessionModel> {
                override fun onResponse(
                    call: Call<SessionModel>,
                    response: Response<SessionModel>
                ) {
                    if (response.isSuccessful) {

                        println("Response " + Gson().toJson(response.body()))
                        response.body().toString()
                        loginResult = response.body()
                    } else {
                        println("Response fail : " + response.code())
                        loginResult = null
                    }

                }

                override fun onFailure(call: Call<SessionModel>, t: Throwable) {
                    println("Errore : " + t.message)
                    loginResult = null
                }

            })

        return loginResult
    }*/

    suspend fun login(username: String, password: String): SessionModel? = withContext(
        Dispatchers.IO) {
        var loginResult: SessionModel? = null

        try {
            val response = client?.login("francesco.paciello@digitarca.it", "Testunityar")?.awaitResponse()
            if (response != null && response.isSuccessful) {
                println("Response " + Gson().toJson(response.body()))
                loginResult = response.body()
                currentSession = response.body()
            } else {
                println("Response fail : " + response?.code())
            }
        } catch (t: Throwable) {
            println("Error: " + t.message)
        }

        loginResult
    }

    suspend fun getAssets(sessionID : String) : AssetModel? = withContext(Dispatchers.IO){

        var assets: AssetModel? = null

        try {
            if (currentSession != null) {
                client?.getAssets(sessionID, 1)?.enqueue(object  : Callback<AssetModel> {
                    override fun onResponse(
                        call: Call<AssetModel>,
                        response: Response<AssetModel>
                    ) {
                        if(response.isSuccessful){
                            assets = response.body()
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

    suspend fun getProjectList() : ProjectModel? = withContext(Dispatchers.IO) {

        if (currentSession != null) {
            client?.getProjects(currentSession!!.SessionID)
                ?.enqueue(object : Callback<ProjectModel> {
                    override fun onResponse(
                        call: Call<ProjectModel>,
                        response: Response<ProjectModel>
                    ) {
                        if (response.isSuccessful) {
                            projects = response.body()
                            response.body().let {
                                if (it != null) {
                                    for (project in it.Data) {
                                        println(project.Name)
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ProjectModel>, t: Throwable) {
                        println("Errore lista progetti : " + t.message)
                    }

                })
        }

        projects
    }
}