package com.codingtest.currrencyconversionapp.base.network


import android.content.Context
import com.codingtest.currrencyconversionapp.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

fun createNetworkClient(context: Context) = retrofitClient(httpClient(context))

fun httpClient(context: Context): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    val interceptor = Interceptor { chain ->
        val request = chain?.request()?.newBuilder()
            ?.addHeader("Content-Type", "application/json")
            ?.addHeader("Accept", "application/json")
            ?.build()
        chain?.proceed(request)
    }

    return if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(interceptor)
            .build()
    } else {
        OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(interceptor)
            .build()
    }

}

fun retrofitClient(httpClient: OkHttpClient): Retrofit {
    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}