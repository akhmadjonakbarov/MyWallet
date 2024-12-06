package com.akbarovdev.mywallet.features.settings.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.settings.ui.components.SettingItem

@Composable
fun HomeScreen(navController: NavController, innerNavController: NavController) {
    val configuration = LocalConfiguration.current
    Scaffold(topBar = {
        AppBar(navController = navController, title = "Settings")
    }) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .padding((configuration.screenWidthDp * 0.02).dp)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                SettingItem("Language") {}
                SettingItem("About App") {
                    innerNavController.navigate(SettingScreen.aboutapp)
                }
            }
        }
    }
}