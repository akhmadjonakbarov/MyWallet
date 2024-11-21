package com.akbarovdev.mywallt.features.wallet.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

import com.akbarovdev.mywallt.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallt.features.wallet.ui.components.Balance
import com.akbarovdev.mywallt.features.wallet.ui.components.ExpenseDialog
import com.akbarovdev.mywallt.features.wallet.ui.view_model.WalletViewModel
import com.akbarovdev.mywallt.ui.view_models.DialogViewModel
import kotlinx.coroutines.launch

@SuppressLint("NewApi", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    walletViewModel: WalletViewModel = hiltViewModel<WalletViewModel>(),
    dialogViewModel: DialogViewModel = hiltViewModel<DialogViewModel>()
) {
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val state = walletViewModel.expanseState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "Wallet") })
    },

        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    dialogViewModel.openDialog()
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
            Balance(walletViewModel, 85000.0, configuration)
        }
        ExpenseDialog(isOpen = dialogViewModel.isDialogOpen.value,
            onDismiss = { dialogViewModel.closeDialog() },
            onSave = { expense ->
                if (state.value.currentExpanse != null) {
                    // Handle Edit Logic
                } else {
                    walletViewModel.addExpanse(expense)
                    scope.launch {
                        snackBarHostState.showSnackbar("Chiqim qo'shildi")
                    }
                }

            }
        )
    }
    AppSnackBarHostState(
        snackBarHostState = snackBarHostState, snackBarColor = null,
    )

}


@Preview
@Composable
fun WalletScreenPreview() {
    WalletScreen()
}