package com.codingtest.currrencyconversionapp.feature.currencyConversion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codingtest.currrencyconversionapp.data.model.local.CurrencyModel
import com.codingtest.currrencyconversionapp.data.model.local.KeyValue
import com.codingtest.currrencyconversionapp.data.network.CurrencyConversationUseCase
import com.codingtest.currrencyconversionapp.feature.currencyConversion.CurrencyConversationEvent
import com.codingtest.currrencyconversionapp.base.view.BaseViewModel
import kotlinx.coroutines.launch

class CurrencyConversationViewModel(private val currencyConversationUseCase: CurrencyConversationUseCase) :
    BaseViewModel<CurrencyConversationEvent>() {

    val loading = MutableLiveData<Boolean>()
    var quoteList = MutableLiveData<ArrayList<KeyValue>>()
    var localCurrency = MutableLiveData<ArrayList<CurrencyModel>>()

    fun getCurrencyLive() {
        viewModelScope.launch {
            loading.value = true
            currencyConversationUseCase.getCurrencyLive().fold({
                loading.value = false
                emit(CurrencyConversationEvent.FetchCurrencyLiveError(it))
            }, { liveResponse ->
                loading.value = false
                emit(CurrencyConversationEvent.FetchCurrencyLiveSuccess(liveResponse))
            })
        }
    }

    fun getCurrencyList() {
        viewModelScope.launch {
            loading.value = true
            currencyConversationUseCase.getCurrencyList().fold({
                loading.value = false
                emit(CurrencyConversationEvent.FetchCurrencyListError(it))
            }, { listResponse ->
                loading.value = false
                emit(CurrencyConversationEvent.FetchCurrencyListSuccess(listResponse))
            })
        }
    }
}