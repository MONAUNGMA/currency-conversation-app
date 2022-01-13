package com.codingtest.currrencyconversionapp.base.network

import com.codingtest.currrencyconversionapp.data.model.response.CurrencyListResponse
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyLiveResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("live")
    suspend fun getCurrencyLive(@Query("access_key") accessKey: String): CurrencyLiveResponse

    @GET("list")
    suspend fun getCurrencyList(@Query("access_key") accessKey: String): CurrencyListResponse
}