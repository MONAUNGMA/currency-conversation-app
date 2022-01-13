package com.codingtest.currrencyconversionapp.feature.currencyConversion.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingtest.currrencyconversionapp.data.model.local.CurrencyModel

import com.codingtest.currrencyconversionapp.data.model.response.CurrencyListResponse
import com.codingtest.currrencyconversionapp.data.model.response.CurrencyLiveResponse
import com.codingtest.currrencyconversionapp.data.model.local.KeyValue
import com.codingtest.currrencyconversionapp.feature.currencyConversion.adapter.CurrencyAdapter
import com.codingtest.currrencyconversionapp.feature.currencyConversion.viewmodel.CurrencyConversationViewModel
import com.codingtest.currrencyconversionapp.utils.SharedPref
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_currency_conversation.view.*
import java.util.*
import kotlin.collections.ArrayList

class CurrencyConversationViewHelper(
    private val context: Context,
    private var dataHelper: CurrencyConversationDataHelper
) {
    private lateinit var viewModel: CurrencyConversationViewModel
    private lateinit var view: View
    private lateinit var requestActivity: FragmentActivity

    fun bindView(
        requestActivity: FragmentActivity,
        view: View,
        viewModel: CurrencyConversationViewModel,
    ) {
        this.requestActivity = requestActivity
        this.viewModel = viewModel
        this.view = view

        if (!SharedPref(context).getCurrencyList().isNullOrEmpty()) {
            dataHelper.currencyList = SharedPref(context).getCurrencyList()!!
        }

        if (!SharedPref(context).getCurrencyLive().isNullOrEmpty()) {
            viewModel.quoteList.postValue(SharedPref(context).getCurrencyLive())
        }

        view.input_amount.addTextChangedListener(currencyWatcher)
    }

    private val currencyWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s != null){
                val str = s.toString()
                with(view.input_amount){
                    if(str.contains(",")) replaceInput(this, str, ",")
                    if(str.contains("-")) replaceInput(this, str, "-")
                    if(str.contains(" ")) replaceInput(this, str, " ")

                    if (str.filter { dot -> dot == '.' }.length > 1) {
                        this.setText(str.dropLast(1))
                        this.setSelection(str.length - 1)
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {

                val str = s.toString()
                if ( s.isNotEmpty() && !str.contains(" ") &&  !str.contains("-") && !str.contains(",") && str.filter { dot -> dot == '.' }.length <= 1) {

                    val currency = ArrayList<CurrencyModel>()
                    dataHelper.currencyLive.forEach {
                        val name: String =
                            dataHelper.currencyList.single { s -> s.key == it.key.drop(3) }.value

                        var currencyValue = str.toDouble()
                        if (it.key != dataHelper.currentSelectedValue!!.key) {
                            currencyValue = it.value.toDouble() * (str.toDouble() / dataHelper.currentSelectedValue!!.value.toDouble())
                        }
                        currency.add(CurrencyModel(it.key, currencyValue.toBigDecimal(), name))
                    }
                    viewModel.localCurrency.postValue(currency)
                }
            }
        }
    }

    private fun replaceInput(v: EditText, value: String, oldValue: String){
        with(v){
            this.setText(value.replace(oldValue, ""))
            this.setSelection(value.length - 1)
        }
    }

    fun bindSpinnerView(data: ArrayList<KeyValue>) {
        val key = ArrayList<String>()

        data.forEach { keyValue ->
            key.add(keyValue.key)
        }

        //init data
        dataHelper.currentSelectedValue = data[0]

        view.currency_from_spinner.setItems(key)
        view.currency_from_spinner.setOnItemSelectedListener { _, position, _, item ->
            dataHelper.currentSelectedValue = data[position]
        }

        addCurrencyData(data)
    }

    private fun addCurrencyData(currencyLive: ArrayList<KeyValue>) {
        //for local data
        dataHelper.currencyLive = currencyLive

        if (dataHelper.currencyList.isNotEmpty()) {
            val currency = ArrayList<CurrencyModel>()
            currencyLive.forEach {
                val name: String =
                    dataHelper.currencyList.single { s -> s.key == it.key.drop(3) }.value
                currency.add(CurrencyModel(it.key, it.value.toBigDecimal(), name))
            }
            viewModel.localCurrency.postValue(currency)
        }
    }

    fun bindRecyclerview(data: List<CurrencyModel>) {
        if (view.currency_recy is RecyclerView) {
            with(view.currency_recy) {
                this.apply {
                    layoutManager = LinearLayoutManager(context)
                    itemAnimator = DefaultItemAnimator()
                }
                this.adapter = CurrencyAdapter(data)
            }
        }
    }

    fun liveCurrency(responseCurrency: CurrencyLiveResponse) {
        if (responseCurrency.success) {
            val quotes = convertToKeyValue(responseCurrency.quotes)
            SharedPref(context).setCurrencyLive(quotes)
            SharedPref(context).setLastUpdateTime(Date())
            viewModel.quoteList.postValue(quotes)//init
        } else viewModel.getCurrencyLive()
    }

    fun listCurrency(responseCurrency: CurrencyListResponse) {
        if (responseCurrency.success) {
            val currencies = convertToKeyValue(responseCurrency.currencies)
            SharedPref(context).setCurrencyList(currencies)
            dataHelper.currencyList = currencies//init
        } else viewModel.getCurrencyList()
    }

    fun isAfterRefreshTimeRate(): Boolean {
        val diff = Date().time - SharedPref(context).getLastUpdateTime()!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        return minutes > 30
    }

    private fun convertToKeyValue(data: Any): ArrayList<KeyValue> {
        val keyValue: ArrayList<KeyValue> = ArrayList<KeyValue>()
        val obj = Gson().toJsonTree(data).asJsonObject
        for ((key, value) in obj.entrySet()) {
            keyValue.add(KeyValue(key, value.asString))
        }
        return keyValue
    }
}