package com.codingtest.currrencyconversionapp.feature.currencyConversion

import com.codingtest.currrencyconversionapp.data.model.response.CurrencyListResponse
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyLiveResponse
import com.codingtest.currrencyconversionapp.utils.ApiException

sealed class CurrencyConversationEvent {
    data class FetchCurrencyLiveError(val error: ApiException) : CurrencyConversationEvent()
    data class FetchCurrencyLiveSuccess(val currencyLiveResponse: CurrencyLiveResponse) : CurrencyConversationEvent()

    data class FetchCurrencyListError(val error: ApiException) : CurrencyConversationEvent()
    data class FetchCurrencyListSuccess(val currencyListResponse: CurrencyListResponse) : CurrencyConversationEvent()
}