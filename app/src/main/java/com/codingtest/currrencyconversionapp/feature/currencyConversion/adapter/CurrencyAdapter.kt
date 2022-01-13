package com.codingtest.currrencyconversionapp.feature.currencyConversion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingtest.currrencyconversionapp.data.model.local.CurrencyModel
import com.codingtest.currrencyconversionapp.databinding.CurrencyListBinding

class CurrencyAdapter(private val currencies: List<CurrencyModel>) : RecyclerView.Adapter<CurrencyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.bindData(currency)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }
}

class CurrencyViewHolder(private val binding: CurrencyListBinding) : RecyclerView.ViewHolder(binding.root){
    fun bindData(currencies: CurrencyModel){
        binding.currency = currencies
        binding.executePendingBindings()
    }
}