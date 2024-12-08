package com.akbarovdev.mywallet.features

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.budget.ui.BudgetScreen
import com.akbarovdev.mywallet.features.currency.ui.CurrencyManagerScreen
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.ui.PersonDetailScreen
import com.akbarovdev.mywallet.features.debt.ui.PersonScreen
import com.akbarovdev.mywallet.features.settings.ui.SettingScreen
import com.akbarovdev.mywallet.features.statistics.ui.StatisticsScreen
import com.akbarovdev.mywallet.features.ui.SplashScreen
import com.akbarovdev.mywallet.features.wallet.ui.WalletScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun WalletApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarManager =
        remember { SnackBarManager(snackBarHostState, scope) }
    NavHost(
        navController = navController,
        startDestination = Screens.splash
    ) {
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
        composable(Screens.currencyManager) {
            CurrencyManagerScreen(navController, snackBarManager)
        }
        composable(SettingScreen.home) {
            SettingScreen(navController)
        }
        composable(Screens.person) {
            PersonScreen(navController, snackBarManager) { person ->
                val personJson = Json.encodeToString(person)
                navController.navigate("person/$personJson")
            }
        }
        composable(
            Screens.personDetail,
            arguments = listOf(navArgument("personJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val personJson = backStackEntry.arguments?.getString("personJson")
            val person = Json.decodeFromString<PersonModel>(personJson.toString())
            PersonDetailScreen(
                navController, person, snackBarManager
            )
        }
    }
}