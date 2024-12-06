package com.akbarovdev.mywallet.features.budget.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.utils.DateFormatter
import com.akbarovdev.mywallet.utils.NumberFormat
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun BudgetScreen(
    navController: NavController,
    budgetViewModel: BudgetViewModel = hiltViewModel<BudgetViewModel>(),
    currencyManagerViewModel: CurrencyManagerViewModel = hiltViewModel<CurrencyManagerViewModel>()
) {

    val state = budgetViewModel.state.collectAsState()
    val currencyState = currencyManagerViewModel.state.collectAsState()
    val budget = remember { mutableStateOf(BudgetModel.empty()) }
    Scaffold(topBar = {
        AppBar(
            title = "Budget Manager", navController = navController
        )
    }) { contentPadding ->

        Box(modifier = Modifier.padding(contentPadding)) {
            when {
                state.value.loading -> {
                    CircularProgressIndicator()
                }

                state.value.list.isNotEmpty() -> {
                    val totalBudgets = state.value.list.sumOf { it.amount }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Total budget",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700)
                                )
                                Text(
                                    "${NumberFormat.format(totalBudgets)} ${currencyState.value.currency.name}",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700)
                                )
                            }
                        }
                        items(state.value.list.size) { index ->
                            val item = state.value.list[index]
                            val currencyType = currencyState.value.currency.name
                            BudgetItem(item, currencyType, onDelete = {
                                budget.value = item
                                budgetViewModel.openDialog()
                            })
                        }
                    }
                }

                else -> {
                    AlertTextBox("No budgets", modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    if (budgetViewModel.isOpenDialog.value) {
        AlertDialog(
            title = {
                Text(
                    "${NumberFormat.format(budget.value.amount)} ${currencyState.value.currency.name}",
                )
            },
            text = {
                Text("Do you want to delete?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        budgetViewModel.deleteBudget(budget.value)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        budgetViewModel.closeDialog()
                        budget.value = BudgetModel.empty()
                    }
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = "Delete")
                }
            },
            onDismissRequest = { budgetViewModel.closeDialog() },
        )
    }
}


@SuppressLint("NewApi")
@Composable
private fun BudgetItem(
    budget: BudgetModel, currencyType: String, padding: Dp = 20.dp, onDelete: () -> Unit
) {
    // State for alpha animation
    val alphaAnimation = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Start the fade-in animation when the composable is launched
        scope.launch {
            alphaAnimation.animateTo(
                targetValue = 1f, animationSpec = tween(
                    durationMillis = 1250,  // Duration of the fade-in effect
                    delayMillis = 250,      // Delay before animation starts
                    easing = LinearEasing   // Easing function for smooth transition
                )
            )
        }
    }



    Box(modifier = Modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onTap = { onDelete() })
        }
        .graphicsLayer {
            alpha = alphaAnimation.value
        }
        .padding(padding)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                Text(
                    DateFormatter.format(LocalDateTime.parse(budget.date)),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    DateFormatter.formatWithDayHour(LocalDateTime.parse(budget.date)),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                "${NumberFormat.format(budget.amount)} $currencyType",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
            )
        }
    }
}