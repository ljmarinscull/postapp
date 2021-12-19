package com.lj.postapp.di

import com.lj.postapp.data.IBackendService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBackendService(retrofit: Retrofit): IBackendService {
        return retrofit.create(IBackendService::class.java)
    }

    @Singleton
    @Provides
    fun provideBackendRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient()
    }
}