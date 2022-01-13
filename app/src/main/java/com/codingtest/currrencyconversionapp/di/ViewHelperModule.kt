package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.feature.currencyConversion.view.CurrencyConversationViewHelper
import org.koin.dsl.module

val viewHelperModule = module {
    single { CurrencyConversationViewHelper(get(), get()) }
}