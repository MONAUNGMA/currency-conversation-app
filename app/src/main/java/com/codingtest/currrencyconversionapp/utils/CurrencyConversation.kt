package com.codingtest.currrencyconversionapp.utils

import android.app.Application
import com.codingtest.currrencyconversionapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class CurrencyConversation : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyConversation)
            loadKoinModules(
                arrayListOf(
                    networkModule,
                    remoteModule,
                    useCaseModule,
                    viewHelperModule,
                    dataHelperModule,
                    viewModelModule,
                    otherModule
                )
            )
        }
    }
}