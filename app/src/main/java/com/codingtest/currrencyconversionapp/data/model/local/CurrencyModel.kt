package com.codingtest.currrencyconversionapp.data.model.local

import java.math.BigDecimal

data class CurrencyModel(
    val key: String,
    val value: BigDecimal,
    val name: String
)