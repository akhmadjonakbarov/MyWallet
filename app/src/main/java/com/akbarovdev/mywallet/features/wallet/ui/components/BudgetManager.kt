package com.akbarovdev.mywallet.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.budget.ui.view_model.BudgetViewModel
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallet.features.wallet.ui.view_model.WalletViewModel
import com.akbarovdev.mywallet.utils.DateFormatter
import com.akbarovdev.mywallet.utils.NumberFormat
import java.time.Instant
import java.time.ZoneId


@SuppressLint("StateFlowValueCalledInComposition", "NewApi")
@Composable
fun BudgetManager(
    expanses: List<ExpanseModel>,
    budgetViewModel: BudgetViewModel,
    walletViewModel: WalletViewModel,
    configuration: Configuration,
    openDialog: () -> Unit
) {

    var showDateTimePickerDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .height((configuration.screenHeightDp * 0.8).dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 25.dp, topEnd = 25.dp
                )
            )

            .background(Color(0xFF8B5DFF))

            .fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Budget(
                    openDialog = openDialog,
                    balance = budgetViewModel.state.value.lastAddedBudgetValue
                )
            }
            Spacer(
                Modifier.height(15.dp)
            )
            ProgressBar(
                budgetViewModel.state.value.percentage,
                configuration
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 25.dp, topEnd = 25.dp
                    )
                )
                .height((configuration.screenHeightDp * 0.67).dp)
                .background(color = Color.White),
        ) {


            val totalPrice = if (expanses.isNotEmpty()) expanses.sumOf { expanse ->
                expanse.price * expanse.qty
            } else 0.0
            Column {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .fillMaxWidth()

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier

                                .clip(RoundedCornerShape(5.dp))
                                .clickable(
                                    onClick = {
                                        showDateTimePickerDialog.value = true
                                    }
                                )
                                .padding(5.dp),
                        ) {
                            Text(
                                text = "Expenses",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                            Text(

                                text = DateFormatter.formatWithDay(walletViewModel.selectedDate.value),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold, color = Color.Black
                                ),
                            )
                        }
                        Text(
                            "${NumberFormat.format(totalPrice)} so'm",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold, color = Color.Black
                            ),
                        )
                    }

                }
                when {
                    expanses.isNotEmpty() -> {

                        ExpanseList(
                            expanses, configuration,
                            onLongPress = {

                                walletViewModel.selectExpanse(it)
                            }
                        )
                    }

                    else -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                "Chiqimlar mavjud emas!",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                }

            }
        }
    }
    if (showDateTimePickerDialog.value) {
        DatePickerModal(
            onDateSelected = { millis ->
                if (millis != null) {
                    val date = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())  // or use a specific timezone
                        .toLocalDateTime()
                    walletViewModel.setDate(date)
                }
            }, onDismiss = {
                showDateTimePickerDialog.value = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
