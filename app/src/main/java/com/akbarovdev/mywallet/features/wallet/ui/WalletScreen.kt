package com.akbarovdev.mywallet.features.wallet.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.common.components.HomeAppBar
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallet.features.wallet.ui.components.Balance
import com.akbarovdev.mywallet.features.wallet.ui.components.ExpenseDialog
import com.akbarovdev.mywallet.features.wallet.ui.components.FloatButton
import com.akbarovdev.mywallet.features.wallet.ui.components.MenuDrawer
import com.akbarovdev.mywallet.features.wallet.ui.view_model.WalletViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController: NavController,
    snackBarManager: SnackBarManager,
    scope: CoroutineScope = rememberCoroutineScope(),
    walletViewModel: WalletViewModel = hiltViewModel<WalletViewModel>(),
    budgetViewModel: BudgetViewModel = hiltViewModel<BudgetViewModel>(),
    currencyManagerViewModel: CurrencyManagerViewModel = hiltViewModel<CurrencyManagerViewModel>()
) {
    val configuration = LocalConfiguration.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val walletState = walletViewModel.state.collectAsState()
    val budgetState = budgetViewModel.state.collectAsState()


    // float action button animation
    val thirdIsVisible = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (thirdIsVisible.value) 1f else 0f, // Scale from half-size to full-size
        animationSpec = tween(durationMillis = (1000)), label = "float action button animation"
    )

    LaunchedEffect(Unit) {

        budgetViewModel.fetchBudget(onUpdate = {
            scope.launch {
                thirdIsVisible.value = true
            }
        })


    }

    LaunchedEffect(walletState.value.isDeleted) {
        if (walletState.value.isDeleted) {
            snackBarManager.showSnackBar("Expense was deleted successfully") {
                walletViewModel.resetState()
            }
        }
    }


    fun closeDrawer() {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(gesturesEnabled = true,

        drawerState = drawerState, drawerContent = {
            MenuDrawer(configuration, navController) { closeDrawer() }
        }) {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            HomeAppBar {
                scope.launch {
                    drawerState.open()  // Open drawer when navigation icon is clicked
                }
            }
        }, snackbarHost = {
            AppSnackBarHostState(
                snackBarHostState = snackBarManager.snackBarHostState,
                snackBarColor = snackBarManager.snackBarColor,
            )
        },

            floatingActionButton = {
                if (budgetState.value.lastAddedBudgetValue > 0.0) {
                    FloatButton(
                        modifier = Modifier.graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        },
                    ) {
                        walletViewModel.openDialog()
                    }
                }

            }) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Balance(
                    walletViewModel,
                    budgetViewModel,
                    currencyManagerViewModel,
                    configuration,
                    onClick = {
                        scope.launch {
                            delay(500)
                            thirdIsVisible.value = true
                        }
                    })
            }

            ExpenseDialog(isOpen = walletViewModel.isOpenDialog.value,
                expanseModel = if (walletState.value.currentExpanse != null) walletState.value.currentExpanse else null,
                onDismiss = {


                    walletViewModel.selectExpanse()
                    walletViewModel.closeDialog()

                },
                onSave = { expense ->
                    if (walletState.value.currentExpanse != null) {
                        walletViewModel.updateExpanse(expense)

                    } else {
                        walletViewModel.addExpanse(expense) {
                            budgetViewModel.fetchBudget()
                        }
                        snackBarManager.showSnackBar("Expanses has been added")
                    }
                }
            )

        }

    }
}

