package com.codingtest.currrencyconversionapp.data.network

import com.codingtest.currrencyconversionapp.BuildConfig
import com.codingtest.currrencyconversionapp.base.network.BaseRemoteRepository
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyListResponse
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyLiveResponse
import com.codingtest.currrencyconversionapp.utils.ApiException
import com.codingtest.currrencyconversionapp.utils.Either

interface CurrencyConversationRepository{
    suspend fun getCurrencyLive(): Either<ApiException, Any>
    suspend fun getCurrencyList(): Either<ApiException, Any>
}

class CurrencyConversationUseCase(
    private val currencyConversationRemote: CurrencyConversationRemote
) : CurrencyConversationRepository, BaseRemoteRepository(){

    override suspend fun getCurrencyLive(): Either<ApiException, CurrencyLiveResponse> {
        return safeApiCall{
            currencyConversationRemote.getCurrencyLive(BuildConfig.CURRENCY_API_KEY)
        }
    }

    override suspend fun getCurrencyList(): Either<ApiException, CurrencyListResponse> {
        return safeApiCall {
            currencyConversationRemote.getCurrencyList(BuildConfig.CURRENCY_API_KEY)
        }
    }
}