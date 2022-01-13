package com.codingtest.currrencyconversionapp.base.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codingtest.currrencyconversionapp.MainActivity

abstract class BaseFragment<VM : BaseViewModel<E>, E> : Fragment() {

    lateinit var baseActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().steamEvent().observe(viewLifecycleOwner, Observer {
            renderEvent(it)
        })
    }

    abstract fun getViewModel(): VM
    abstract fun renderEvent(e: E)
}