package com.akbarovdev.mywallet.features.settings.ui

import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.settings.ui.screens.AboutApp
import com.akbarovdev.mywallet.features.settings.ui.screens.HomeScreen


@Composable
fun SettingScreen(
    navController: NavController,
    snackBarManager: SnackBarManager
) {
    val innerNavController = rememberNavController()
    NavHost(
        navController = innerNavController, startDestination = SettingScreen.home
    ) {
        composable(SettingScreen.home) {
            HomeScreen(navController, innerNavController, snackBarManager)
        }
        composable(SettingScreen.language) {
        }
        composable(SettingScreen.aboutapp) {
            AboutApp(innerNavController)
        }
    }
}


