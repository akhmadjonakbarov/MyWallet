package com.akbarovdev.mywallet.features.currency.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.core.database.sharedPreferences
import com.akbarovdev.mywallet.features.currency.domain.model.CurrencyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyManagerViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    data class CurrencyState(
        val currency: CurrencyModel = CurrencyModel(
            "USD", isSelected = true,
        ),
        val currencyTypes: List<CurrencyModel> = listOf(
            CurrencyModel("USD"), CurrencyModel("UZS")
        )
    )

    private val _state = MutableStateFlow(CurrencyState())
    val state = _state.asStateFlow()


    companion object {
        const val CURRENCY_TYPE = "currency_type"
    }

    // Use the sharedPreferences delegate for username
    var currency by context.sharedPreferences(CURRENCY_TYPE, defaultValue = "UZS")


    init {
        check()
    }

    fun fetchTypes() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currencyTypes = it.currencyTypes
                )
            }
        }
    }

    fun check() {
        viewModelScope.launch {
            val cry = _state.value.currencyTypes.first {
                it.name == currency
            }
            val selectedCurrency = cry.copy(
                isSelected = true, name = cry.name
            )
            _state.update {
                it.copy(
                    currencyTypes = it.currencyTypes,
                    currency = selectedCurrency
                )
            }
        }
    }


    fun selectCurrency(currency: CurrencyModel) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currency = currency
                )
            }
        }
    }
}
