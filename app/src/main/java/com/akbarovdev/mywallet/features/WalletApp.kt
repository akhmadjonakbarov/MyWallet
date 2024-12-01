package com.akbarovdev.mywallet.features

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.features.budget.ui.BudgetScreen
import com.akbarovdev.mywallet.features.statistics.ui.StatisticsScreen
import com.akbarovdev.mywallet.features.wallet.ui.WalletScreen

@Composable
fun WalletApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.wallet) {
        composable(Screens.wallet) {
            WalletScreen(navController)
        }
        composable(Screens.statistics) {
            StatisticsScreen(navController)
        }
        composable(Screens.budget) {
            BudgetScreen(navController)
        }
    }
}