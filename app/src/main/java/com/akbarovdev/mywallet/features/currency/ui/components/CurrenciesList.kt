package com.akbarovdev.mywallet.features.currency.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.akbarovdev.mywallet.features.currency.domain.model.CurrencyModel

@Composable
fun CurrenciesList(
    currencies: List<CurrencyModel>,
    currencyName: String,
    onSelect: (currency: CurrencyModel) -> Unit
) {
    LazyColumn {
        items(currencies.size) { index ->
            val currency = currencies[index]
            CurrencyTypeItem(
                currencyType = currency,
                isSelected = currency.name == currencyName,
                onSelect = {
                    onSelect(it)
                }
            )
        }
    }
}