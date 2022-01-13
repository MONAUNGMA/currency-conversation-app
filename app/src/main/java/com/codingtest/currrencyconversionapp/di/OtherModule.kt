package com.codingtest.currrencyconversionapp.di

import com.codingtest.currrencyconversionapp.utils.SharedPref
import org.koin.dsl.module

val otherModule = module {
    single { SharedPref(get()) }
}