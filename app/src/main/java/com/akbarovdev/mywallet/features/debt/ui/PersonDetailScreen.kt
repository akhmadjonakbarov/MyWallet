package com.akbarovdev.mywallet.features.debt.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.common.components.DeleteButton
import com.akbarovdev.mywallet.features.common.components.EditButton
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.ui.components.DebtEditDialog
import com.akbarovdev.mywallet.features.debt.ui.view_models.DebtViewModel
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallet.features.wallet.ui.components.FloatButton
import com.akbarovdev.mywallet.utils.DateFormatter
import com.akbarovdev.mywallet.utils.NumberFormat
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Composable
fun PersonDetailScreen(
    navController: NavController,
    person: PersonModel,
    snackBarManager: SnackBarManager,
    debtViewModel: DebtViewModel = hiltViewModel<DebtViewModel>(),
    currencyManagerViewModel: CurrencyManagerViewModel = hiltViewModel<CurrencyManagerViewModel>()
) {

    val state = debtViewModel.state.collectAsState()
    val currencyState = currencyManagerViewModel.state.collectAsState()
    val configuration = LocalConfiguration.current

    var deletedDialog by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    val animatedText = animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f, animationSpec = tween(
            durationMillis = 600,
            easing = LinearEasing
        ),
        label = ""
    )

    LaunchedEffect(Unit) {
        debtViewModel.selectPerson(person)
    }

    Scaffold(
        topBar = {
            AppBar(
                navController = navController, title = "${person.name}-Debts"
            )
        },
        snackbarHost = {
            AppSnackBarHostState(
                snackBarManager.snackBarHostState, snackBarColor = snackBarManager.snackBarColor
            )
        },
        floatingActionButton = {
            FloatButton {
                debtViewModel.openDialog()
            }
        },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            when {
                state.value.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.value.debts.isNotEmpty() -> {

                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total:", style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                "${NumberFormat.format(state.value.total)} ${currencyState.value.currency.name}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.W600
                                )
                            )
                        }
                        LazyColumn {

                            items(state.value.debts.size) {
                                val debt = state.value.debts[it]
                                val textDecoration =
                                    if (debt.isPaid) TextDecoration.LineThrough else TextDecoration.None
                                Box(modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectTapGestures(onDoubleTap = {
                                            debtViewModel.pay(debt) {
                                                snackBarManager.showSnackBar(
                                                    "Debt was paid", isAlert = true
                                                )
                                            }
                                        }, onLongPress = {
                                            debtViewModel.unPay(debt)
                                        })
                                    }
                                    .fillMaxWidth()
                                    .padding(
                                        8.dp
                                    ), contentAlignment = Alignment.Center) {
                                    Column {
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .clickable { isExpanded = !isExpanded },
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(shape = CircleShape)
                                                        .background(color = Color.LightGray)

                                                        .padding(8.dp)
                                                ) {
                                                    Icon(
                                                        Icons.Default.MoneyOff,
                                                        contentDescription = null
                                                    )
                                                }
                                                Spacer(Modifier.width((configuration.screenWidthDp / 40).dp))
                                                Column {
                                                    Text(
                                                        "${NumberFormat.format(debt.amount)} ${currencyState.value.currency.name}",
                                                        style = MaterialTheme.typography.bodyLarge.copy(
                                                            fontWeight = FontWeight.Bold,
                                                            textDecoration = textDecoration
                                                        )
                                                    )
                                                    Text(
                                                        DateFormatter.formatWithDayHour(
                                                            LocalDateTime.now()
                                                        ),
                                                        style = MaterialTheme.typography.labelLarge
                                                    )
                                                }
                                            }
                                            Row {
                                                EditButton {
                                                    debtViewModel.selectDebt(debt) {
                                                        debtViewModel.openDialog()
                                                    }
                                                }
                                                DeleteButton {
                                                    debtViewModel.selectDebt(debt)
                                                    deletedDialog = true
                                                }
                                            }

                                        }
                                        if (isExpanded) {
                                            Box(
                                                Modifier
                                                    .padding(
                                                        top = 5.dp, start = 5.dp
                                                    )
                                                    .graphicsLayer {
                                                        alpha = animatedText.value
                                                    }
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    debt.description,
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.W900
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                }

                else -> {
                    AlertTextBox(
                        modifier = Modifier.fillMaxSize(), title = "Debts don't exist!"
                    )
                }
            }
        }

    }



    if (debtViewModel.isOpenDialog.value) {
        DebtEditDialog(debtModel = state.value.selectedDebt,
            isOpen = debtViewModel.isOpenDialog.value,
            onSave = { debt ->
                val selectedDebt = state.value.selectedDebt
                if (selectedDebt.id != -1) {
                    val updatedDebt = debt.copy(
                        personId = selectedDebt.personId, id = selectedDebt.id
                    )
                    debtViewModel.update(updatedDebt)
                    snackBarManager.showSnackBar("Debt was updated successfully")

                } else {
                    debtViewModel.add(debt)
                }
            },
            onDismiss = {
                debtViewModel.closeDialog()
            })
    }

    if (deletedDialog) {
        AlertDialog(
            title = {
                Text(
                    "${NumberFormat.format(state.value.selectedDebt.amount)} ${currencyState.value.currency.name}",
                )
            },
            text = {
                Text("Do you want to delete?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        debtViewModel.delete(state.value.selectedDebt)
                        deletedDialog = false
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                }
            },
            dismissButton = {
                Button(onClick = {
                    deletedDialog = false
                    debtViewModel.selectDebt(
                        DebtModel.empty()
                    )
                }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Delete")
                }
            },
            onDismissRequest = {
                deletedDialog = false
            },
        )
    }
}
