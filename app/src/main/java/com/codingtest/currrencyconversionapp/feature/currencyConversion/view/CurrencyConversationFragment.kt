package com.codingtest.currrencyconversionapp.feature.currencyConversion.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.codingtest.currrencyconversionapp.R
import com.codingtest.currrencyconversionapp.feature.currencyConversion.CurrencyConversationEvent
import com.codingtest.currrencyconversionapp.feature.currencyConversion.viewmodel.CurrencyConversationViewModel
import com.codingtest.currrencyconversionapp.base.view.BaseFragment
import com.codingtest.currrencyconversionapp.utils.SharedPref
import com.kaopiz.kprogresshud.KProgressHUD
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import java.util.*

class CurrencyConversationFragment :
    BaseFragment<CurrencyConversationViewModel, CurrencyConversationEvent>() {

    private lateinit var rootView: View
    private val currencyConversationViewModel: CurrencyConversationViewModel by inject()
    private val viewHelper: CurrencyConversationViewHelper by inject()
    lateinit var hud: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_currency_conversation, container, false)

        initView()
        observer()

        viewHelper.bindView(
            this.requireActivity(),
            rootView,
            currencyConversationViewModel
        )

        return rootView
    }

    private fun initView() {

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Loading")

        if (SharedPref(this.requireContext()).getCurrencyList() == null) {
            currencyConversationViewModel.getCurrencyList()
        }

        if (SharedPref(this.requireContext()).getCurrencyLive() == null) {
            currencyConversationViewModel.getCurrencyLive()
        }

        if (Date().after(SharedPref(this.requireContext()).getLastUpdateTime())) {
            if (viewHelper.isAfterRefreshTimeRate()) {
                currencyConversationViewModel.getCurrencyLive()
            }
        }
    }

    private fun observer() {
        currencyConversationViewModel.quoteList.observe(viewLifecycleOwner, Observer { data ->
            viewHelper.bindSpinnerView(data)
        })

        currencyConversationViewModel.localCurrency.observe(viewLifecycleOwner, Observer { data ->
            viewHelper.bindRecyclerview(data)
        })

        currencyConversationViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) hud.show() else hud.dismiss()
        })
    }

    override fun getViewModel(): CurrencyConversationViewModel {
        return currencyConversationViewModel
    }

    override fun renderEvent(events: CurrencyConversationEvent) {
        when (events) {
            is CurrencyConversationEvent.FetchCurrencyLiveSuccess -> {
                viewHelper.liveCurrency(events.currencyLiveResponse)
            }
            is CurrencyConversationEvent.FetchCurrencyLiveError -> {
                toast(events.error.errorMessage)
            }

            is CurrencyConversationEvent.FetchCurrencyListSuccess -> {
                viewHelper.listCurrency(events.currencyListResponse)
            }
            is CurrencyConversationEvent.FetchCurrencyListError -> {
                toast(events.error.errorMessage)
            }
        }
    }
}