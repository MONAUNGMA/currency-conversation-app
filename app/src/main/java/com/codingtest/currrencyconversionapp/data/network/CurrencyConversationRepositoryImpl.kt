package com.codingtest.currrencyconversionapp.data.network

import com.codingtest.currrencyconversionapp.base.network.ApiService
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyListResponse
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyLiveResponse

interface CurrencyConversationRemote{
    suspend fun getCurrencyLive(apiKey: String): CurrencyLiveResponse
    suspend fun getCurrencyList(apiKey: String): CurrencyListResponse
}

class CurrencyConversationRepositoryImpl(private val apiService: ApiService) : CurrencyConversationRemote{
    override suspend fun getCurrencyLive(apiKey: String): CurrencyLiveResponse {
        return apiService.getCurrencyLive(apiKey)
    }

    override suspend fun getCurrencyList(apiKey: String): CurrencyListResponse {
        return apiService.getCurrencyList(apiKey)
    }
}