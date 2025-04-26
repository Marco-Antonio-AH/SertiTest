package com.example.serti.di

import com.example.serti.data.remote.ReqResApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY = "reqres-free-v1"

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val requestWithKey = chain.request().newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(requestWithKey)
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(ok: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .client(ok)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ReqResApi =
        retrofit.create(ReqResApi::class.java)
}
