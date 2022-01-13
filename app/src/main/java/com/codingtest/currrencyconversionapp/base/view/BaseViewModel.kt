package com.codingtest.currrencyconversionapp.base.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<E> : ViewModel() {
    private val events = MutableLiveData<E>()
    fun emit(e: E) {
        viewModelScope.launch {
            events.value = e
        }
    }

    fun steamEvent() = events
}