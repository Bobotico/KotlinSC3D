package com.example.smartcity3dar.dependencyInjection

import com.example.smartcity3dar.networking.ISC3DAPI
import com.example.smartcity3dar.repository.DefaultMainRepository
import com.example.smartcity3dar.repository.MainRepository
import com.example.smartcity3dar.services.DataCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://cloud17.smartcity3d.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule{

    @Singleton
    @Provides
    fun provideSC3DAPI():ISC3DAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ISC3DAPI::class.java)

    @Singleton
    @Provides
    fun provideRepository(sc3dApi:ISC3DAPI, dataCache: DataCache) : MainRepository = DefaultMainRepository(sc3dApi,dataCache)

}