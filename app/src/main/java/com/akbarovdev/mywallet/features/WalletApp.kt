package com.akbarovdev.mywallet.features

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.budget.ui.BudgetScreen
import com.akbarovdev.mywallet.features.settings.ui.SettingScreen
import com.akbarovdev.mywallet.features.splash.ui.SplashScreen
import com.akbarovdev.mywallet.features.statistics.ui.StatisticsScreen
import com.akbarovdev.mywallet.features.wallet.ui.WalletScreen

@Composable
fun WalletApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarManager = remember { SnackBarManager(snackBarHostState, scope) }
    NavHost(navController = navController, startDestination = Screens.splash) {
        composable(Screens.splash) {
            SplashScreen(
                navController, snackBarManager
            )
        }
        composable(Screens.wallet) {
            WalletScreen(navController)
        }
        composable(Screens.statistics) {
            StatisticsScreen(navController)
        }
        composable(Screens.budget) {
            BudgetScreen(navController)
        }
        composable(SettingScreen.home) {
            SettingScreen()
        }
    }
}