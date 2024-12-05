package com.akbarovdev.mywallet.features.currency.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AppBar

@Composable
fun CurrencyManagerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(navController, "Currency Type Manager")
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            when {
                else -> Box {
                    AlertTextBox("No Currency Type")
                }
            }
        }
    }
}