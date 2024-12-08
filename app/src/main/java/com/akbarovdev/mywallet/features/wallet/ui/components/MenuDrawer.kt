package com.akbarovdev.mywallet.features.wallet.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.MoneyOff
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.core.navigation.SettingScreen

@Composable
fun MenuDrawer(
    configuration: Configuration, navController: NavController, onDismiss: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width((configuration.screenWidthDp * 0.6).dp)
            .fillMaxHeight(),
    ) {
        Text(
            "Menu",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalDivider()
        NavigationDrawerItem(shape = RectangleShape, label = {
            MenuItemText("Statistics", Icons.Default.PieChart)
        }, selected = false, onClick = {
            navController.navigate(Screens.statistics)
            onDismiss()
        })
        NavigationDrawerItem(shape = RectangleShape, label = {
            MenuItemText("Budgets", Icons.Default.AttachMoney)
        }, selected = false, onClick = {
            navController.navigate(Screens.budget)
            onDismiss()
        })
        NavigationDrawerItem(shape = RectangleShape, label = {
            MenuItemText("Currency Type Manager", Icons.Default.CurrencyExchange)
        }, selected = false, onClick = {
            navController.navigate(Screens.currencyManager)
            onDismiss()
        })
        NavigationDrawerItem(shape = RectangleShape, label = {
            MenuItemText("Debt Manager", Icons.Outlined.NoteAlt)
        }, selected = false, onClick = {
            navController.navigate(Screens.person)
            onDismiss()
        })
        NavigationDrawerItem(shape = RectangleShape, label = {
            MenuItemText("Settings", Icons.Default.Settings)
        }, selected = false, onClick = {
            navController.navigate(SettingScreen.home)
            onDismiss()
        })

    }
}

@Composable
fun MenuItemText(text: String, icon: ImageVector) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(5.dp))
        Text(text = text)
    }
}