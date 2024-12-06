package com.akbarovdev.mywallet.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.features.wallet.ui.view_model.WalletViewModel
import com.akbarovdev.mywallet.utils.DateFormatter
import com.akbarovdev.mywallet.utils.NumberFormat
import kotlinx.coroutines.delay
import java.time.LocalDateTime


@SuppressLint("NewApi")
@Composable
fun Balance(
    walletViewModel: WalletViewModel,
    budgetViewModel: BudgetViewModel,
    currencyManagerViewModel: CurrencyManagerViewModel,
    configuration: Configuration,
    onClick: () -> Unit
) {

    val budgetState = budgetViewModel.state.collectAsState()
    val walletState = walletViewModel.state.collectAsState()
    val currencyState = currencyManagerViewModel.state.collectAsState()
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    DateFormatter.format(LocalDateTime.now()),
                    style = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(
                    modifier = Modifier.height(25.dp)
                )
                Balance(
                    balance = if (budgetState.value.budget.remained > 0.0) budgetState.value.budget.remained else budgetState.value.budget.amount,
                    currencyType = currencyState.value.currency.name
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            BudgetManager(walletState.value.expanses,
                budgetViewModel,
                walletViewModel,
                currencyState.value.currency.name,
                configuration,
                openDialog = {
                    budgetViewModel.openDialog()
                }
            )
        }
    }
    BudgetDialog(isOpen = budgetViewModel.isOpenDialog.value, onDismiss = {
        budgetViewModel.closeDialog()
        onClick()
    }, onSave = { it ->
        budgetViewModel.addBudget(it)
    })

}


@Composable
private fun Balance(balance: Double, currencyType: String) {

    var isScaled = remember { mutableStateOf(false) }

    val scale = animateFloatAsState(
        targetValue = if (isScaled.value) 1.0f else 0.45f,
        animationSpec = tween(durationMillis = 1000),
        label = "Balance Scale"
    )

    LaunchedEffect(Unit) {
        delay(500)
        isScaled.value = true
    }


    val configuration = LocalConfiguration.current

    Column(
        Modifier
            .graphicsLayer {
                scaleY = scale.value
                scaleX = scale.value
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = NumberFormat.format(balance),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        )

        Box(modifier = Modifier.offset(x = (configuration.screenWidthDp / 10).dp, y = 0.dp)) {
            Text(currencyType.lowercase(), style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }

}
