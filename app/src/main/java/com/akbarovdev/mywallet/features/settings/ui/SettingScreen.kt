package com.akbarovdev.mywallet.features.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.settings.ui.screens.AboutApp
import com.akbarovdev.mywallet.features.settings.ui.screens.HomeScreen


@Composable
fun SettingScreen(

) {
    val innerNavController = rememberNavController()
    NavHost(
        navController = innerNavController, startDestination = SettingScreen.home
    ) {
        composable(SettingScreen.home) {
            HomeScreen(innerNavController)
        }
        composable(SettingScreen.language) {
        }
        composable(SettingScreen.aboutapp) {
            AboutApp(innerNavController)
        }
    }
}


@Preview
@Composable
fun PreviewSettingScreen() {
    SettingScreen()
}