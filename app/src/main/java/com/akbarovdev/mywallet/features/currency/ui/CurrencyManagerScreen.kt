package com.akbarovdev.mywallet.features.currency.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.currency.domain.model.CurrencyModel
import com.akbarovdev.mywallet.features.currency.ui.components.CurrenciesList
import com.akbarovdev.mywallet.features.currency.ui.components.CurrencyTypeItem
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel

@Composable
fun CurrencyManagerScreen(
    navController: NavController,
    viewModel: CurrencyManagerViewModel = hiltViewModel<CurrencyManagerViewModel>()
) {
    val state = viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            AppBar(navController, "Currency Type Manager")
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            when {
                state.value.currencyTypes.isNotEmpty() -> {
                    CurrenciesList(
                        state.value.currencyTypes,
                        state.value.currency.name,
                        onSelect = {
                            {
                                viewModel.selectCurrency(it)
                            }
                        }
                    )
                }

                else ->  {
                    AlertTextBox("No Currency Type")
                }
            }
        }
    }
}


@Preview
@Composable
fun CurrencyManagerScreenPreview() {
    CurrencyTypeItem(
        currencyType = CurrencyModel(
            name = "USD",
        ), onSelect = {}
    )
}