package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akbarovdev.mywallt.features.wallet.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallt.features.wallet.ui.view_model.WalletViewModel
import com.akbarovdev.mywallt.utils.DateFormatter
import com.akbarovdev.mywallt.utils.NumberFormat
import java.time.LocalDateTime


@SuppressLint("NewApi")
@Composable
fun Balance(
    walletViewModel: WalletViewModel,
    budgetViewModel: BudgetViewModel,
    configuration: Configuration,
) {

    val budgetState = budgetViewModel.state.collectAsState()
    val walletState = walletViewModel.expanseState.collectAsState()
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
                    modifier = Modifier.height(16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconBtn(onClick = {

                    }, icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null
                        )
                    })
                    _Balance(
                        balance = if (budgetState.value.budget.remained > 0.0) budgetState.value.budget.remained else budgetState.value.budget.amount
                    )
                    IconBtn(onClick = {}, icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null
                        )
                    })
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            BudgetManager(walletState.value.expanses,
                budgetViewModel,
                walletViewModel,
                configuration,
                openDialog = {
                    budgetViewModel.openDialog()
                }
            )
        }

    }
    BudgetDialog(isOpen = budgetViewModel.isOpenDialog.value, onDismiss = {
        budgetViewModel.closeDialog()
    }, onSave = { it ->
        budgetViewModel.addBudget(it)
    })

}


@Composable
fun _Balance(balance: Double) {

    Column(
        horizontalAlignment = Alignment.End
    ) {


        Text(

            text = NumberFormat.format(balance),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        )

        Text("so'm", style = TextStyle(fontWeight = FontWeight.Bold))
    }

}
