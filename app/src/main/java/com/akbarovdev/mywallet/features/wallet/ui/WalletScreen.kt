package com.akbarovdev.mywallet.features.wallet.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallet.features.wallet.ui.components.Balance
import com.akbarovdev.mywallet.features.wallet.ui.components.ExpenseDialog
import com.akbarovdev.mywallet.features.wallet.ui.view_model.WalletViewModel
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController: NavController,
    walletViewModel: WalletViewModel = hiltViewModel<WalletViewModel>(),
    budgetViewModel: BudgetViewModel = hiltViewModel<BudgetViewModel>()
) {
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val walletState = walletViewModel.expanseState.collectAsState()


    LaunchedEffect(Unit) {
        walletViewModel.fetchExpanses()
        budgetViewModel.fetchBudget()
    }

    fun closeDrawer() {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        gesturesEnabled = true,

        drawerState = drawerState,
        drawerContent = {
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
                NavigationDrawerItem(
                    shape = RectangleShape,
                    label = {
                        MenuItemText("Statistics", Icons.Default.PieChart)
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(Screens.statistics)
                        closeDrawer()
                    }
                )
                NavigationDrawerItem(
                    shape = RectangleShape,
                    label = {
                        MenuItemText("Budgets", Icons.Default.AttachMoney)
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(Screens.budget)
                        closeDrawer()
                    }
                )

                NavigationDrawerItem(
                    shape = RectangleShape,
                    label = {
                        MenuItemText("Currency", Icons.Default.Settings)
                    },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    ) {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = { Text(text = "Wallet") }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()  // Open drawer when navigation icon is clicked
                    }
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                }
            })
        },

            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = {
                        walletViewModel.openDialog()
                    }, containerColor = Color.Blue
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color.White)
                }
            }) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Balance(walletViewModel, budgetViewModel, configuration)
            }

            ExpenseDialog(isOpen = walletViewModel.isOpenDialog.value,
                onDismiss = { walletViewModel.closeDialog() },
                onSave = { expense ->
                    if (walletState.value.currentExpanse != null) {
                        // Handle Edit Logic
                    } else {
                        walletViewModel.addExpanse(expense) {
                            budgetViewModel.fetchBudget()
                        }
                        scope.launch {
                            snackBarHostState.showSnackbar("Expanses has been added")
                        }

                    }

                }
            )

        }
        AppSnackBarHostState(
            snackBarHostState = snackBarHostState, snackBarColor = null,
        )
    }
}

@Composable
fun MenuItemText(text: String, icon: ImageVector) {
    Row {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(5.dp))
        Text(text = text)
    }
}


@Preview
@Composable
fun WalletScreenPreview() {
    WalletScreen(rememberNavController())
}