package com.example.smartcity3dar.dependencyInjection

import com.example.smartcity3dar.services.DataCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataCacheModule {

    @Singleton
    @Provides
    fun provideCache():DataCache = DataCache()

}