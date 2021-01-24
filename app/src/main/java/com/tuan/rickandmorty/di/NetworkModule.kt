package com.tuan.rickandmorty.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.tuan.rickandmorty.network.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient).build()
            .create(Api::class.java)
}