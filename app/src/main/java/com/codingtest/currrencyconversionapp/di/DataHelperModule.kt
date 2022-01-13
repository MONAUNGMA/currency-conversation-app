package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.feature.currencyConversion.view.CurrencyConversationDataHelper
import org.koin.dsl.module

val dataHelperModule = module {
    single { CurrencyConversationDataHelper() }
}