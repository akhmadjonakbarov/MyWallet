package com.akbarovdev.mywallet.features.currency.domain.model

data class CurrencyModel(
    val name: String,
    val isSelected: Boolean = false
)