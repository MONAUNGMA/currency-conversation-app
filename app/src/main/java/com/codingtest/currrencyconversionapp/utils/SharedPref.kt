package com.codingtest.currrencyconversionapp.utils

import android.content.Context
import com.codingtest.currrencyconversionapp.R
import com.codingtest.currrencyconversionapp.data.model.local.KeyValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SharedPref(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val LAST_UPDATE_TIME = "LAST_UPDATE_TIME"
    private val CURRENCY_LIST = "CURRENCY_LIST"
    private val CURRENCY_LIVE = "CURRENCY_LIVE"

    private val datetimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    private val editor = sharedPreferences.edit()

    fun setLastUpdateTime(data: Date) {
        editor.putString(LAST_UPDATE_TIME, datetimeFormat.format(data)).apply()
    }
    fun getLastUpdateTime(): Date? {
        if(sharedPreferences.getString(LAST_UPDATE_TIME, null).isNullOrEmpty())
            setLastUpdateTime(Date())
        return datetimeFormat.parse(sharedPreferences.getString(LAST_UPDATE_TIME, null)!!)
    }

    fun setCurrencyLive(data: ArrayList<KeyValue>) {
        val gson = Gson()
        val json = gson.toJson(data)
        editor.putString(CURRENCY_LIVE, json).apply()
    }

    fun getCurrencyLive(): ArrayList<KeyValue>? {
        val gson = Gson()
        val json = sharedPreferences.getString(CURRENCY_LIVE, null)
        val type =
            object : TypeToken<ArrayList<KeyValue>>() {}.type
        return gson.fromJson(json, type)
    }

    fun setCurrencyList(data: ArrayList<KeyValue>){
        val gson = Gson()
        val json = gson.toJson(data)
        editor.putString(CURRENCY_LIST, json).apply()
    }

    fun getCurrencyList(): ArrayList<KeyValue>? {
        val gson = Gson()
        val json = sharedPreferences.getString(CURRENCY_LIST, null)
        val type =
            object : TypeToken<ArrayList<KeyValue>>() {}.type
        return gson.fromJson(json, type)
    }

}