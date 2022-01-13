package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.base.network.ApiService
import com.codingtest.currrencyconversionapp.base.network.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { createNetworkClient(get()) }
    single { provideApiService(get()) }
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)