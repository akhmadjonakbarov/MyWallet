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
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.core.navigation.SettingScreen
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.settings.ui.components.SettingItem
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState


@Composable
fun HomeScreen(
    navController: NavController,
    innerNavController: NavController,
    snackBarManager: SnackBarManager
) {
    val configuration = LocalConfiguration.current
    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "Settings")
        },
        snackbarHost = {
            AppSnackBarHostState(
                snackBarManager.snackBarHostState, snackBarManager.snackBarColor
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .padding((configuration.screenWidthDp * 0.02).dp)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                SettingItem("Language") {
                    snackBarManager.showSnackBar(
                        "Only English language available!", isAlert = true
                    )
                }
                SettingItem("About App") {
                    innerNavController.navigate(SettingScreen.aboutapp)
                }
            }
        }
    }
}