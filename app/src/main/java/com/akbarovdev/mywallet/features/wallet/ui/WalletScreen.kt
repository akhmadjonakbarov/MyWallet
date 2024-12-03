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
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.common.components.HomeAppBar
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallet.features.wallet.ui.components.Balance
import com.akbarovdev.mywallet.features.wallet.ui.components.ExpenseDialog
import com.akbarovdev.mywallet.features.wallet.ui.components.FloatButton
import com.akbarovdev.mywallet.features.wallet.ui.components.MenuDrawer
import com.akbarovdev.mywallet.features.wallet.ui.view_model.WalletViewModel
import kotlinx.coroutines.delay
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
    val budgetState = budgetViewModel.state.collectAsState()


    // float action button animation
    val thirdIsVisible = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (thirdIsVisible.value) 1f else 0f, // Scale from half-size to full-size
        animationSpec = tween(durationMillis = (1000)), label = "float action button animation"
    )

    LaunchedEffect(Unit) {
        walletViewModel.fetchExpanses()
        budgetViewModel.fetchBudget(onUpdate = {
            scope.launch {
                thirdIsVisible.value = true
            }
        })
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
                Balance(walletViewModel, budgetViewModel, configuration, onClick = {

                    scope.launch {
                        delay(500)
                        thirdIsVisible.value = true
                    }
                })
            }

            ExpenseDialog(isOpen = walletViewModel.isOpenDialog.value,
                expanseModel = if (walletState.value.currentExpanse != null) walletState.value.currentExpanse else null,
                onDismiss = { walletViewModel.closeDialog() },
                onSave = { expense ->
                    val currentExpanse = walletState.value.currentExpanse
                    if (currentExpanse != null) {
                        val updatedExpanse = currentExpanse.copy(
                            price = expense.price,
                            title = expense.title,
                            qty = expense.qty,
                            measure = expense.measure,
                            icon = expense.icon
                        )
                        walletViewModel.updateExpanse(updatedExpanse)
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
            snackBarHostState = snackBarHostState,
        )
    }
}


@Preview
@Composable
fun WalletScreenPreview() {
    WalletScreen(rememberNavController())
}