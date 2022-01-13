package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.data.network.CurrencyConversationRemote
import com.codingtest.currrencyconversionapp.data.network.CurrencyConversationRepositoryImpl
import org.koin.dsl.module

val remoteModule = module {
    factory { CurrencyConversationRepositoryImpl(get()) as CurrencyConversationRemote }
}