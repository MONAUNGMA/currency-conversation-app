package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.data.network.CurrencyConversationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { CurrencyConversationUseCase(get()) }
}