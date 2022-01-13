package com.codingtest.currrencyconversionapp.base.network

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}
