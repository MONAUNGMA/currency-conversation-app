package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.feature.currencyConversion.viewmodel.CurrencyConversationViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CurrencyConversationViewModel(get()) }
}