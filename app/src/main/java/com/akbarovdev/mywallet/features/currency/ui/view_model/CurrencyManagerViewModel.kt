package com.akbarovdev.mywallet.features.currency.ui.view_model

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.core.database.sharedPreferences
import com.akbarovdev.mywallet.features.currency.domain.model.CurrencyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class CurrencyManagerViewModel @Inject constructor(
    @ApplicationContext val context: Context
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

    // Use the sharedPreferences delegate for currency type
    var currency by context.sharedPreferences(CURRENCY_TYPE, defaultValue = "UZS")


    init {
        check()
        fetchTypes()
    }

    fun fetchTypes() {
        viewModelScope.launch {
            val cry = _state.value.currencyTypes.first {
                it.name == currency
            }
            val indexOfCurrency = _state.value.currencyTypes.indexOf(cry)

            val selectedCurrency = cry.copy(
                isSelected = true, name = cry.name
            )
            _state.value.currencyTypes.toMutableList()[indexOfCurrency] = selectedCurrency

            _state.update {
                it.copy(
                    currencyTypes = it.currencyTypes,
                    currency = selectedCurrency
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


    fun selectCurrency(newCurrency: CurrencyModel) {
        viewModelScope.launch {
            try {
                currency = newCurrency.name
                fetchTypes()
                check()
            } catch (e: Exception) {
                Log.e("Currency", "Error: $e")
            }
            delay(1500)
            restartApp()
        }
    }

    fun restartApp() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        exitProcess(0) // Terminates the current process
    }

}
