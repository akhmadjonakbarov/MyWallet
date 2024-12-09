package com.akbarovdev.mywallet.features.debt.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import java.time.LocalDateTime


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun DebtEditDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (DebtModel) -> Unit,
    debtModel: DebtModel
) {


    var amountError by remember { mutableStateOf<String?>(null) }


    if (isOpen) {
        var amount by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }



        if (debtModel.id != -1) {
            amount = debtModel.amount.toString()
            description = debtModel.description
        }

        fun validateFields(): Boolean {
            amountError = if (amount.isEmpty()) "Amount cannot be empty" else null
            return amountError == null
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = if (false) "Edit Debt" else "Add Debt")
            },
            text = {
                Column {
                    // Title TextField
                    TextField(
                        value = amount,
                        onValueChange = {
                            amount = it
                            amountError = if (it.isEmpty()) "Amount cannot be empty" else null
                        },
                        maxLines = 1,
                        placeholder = { Text("Amount") },
                        isError = amountError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    if (amountError != null) {
                        Text(
                            text = amountError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Amount TextField
                    TextField(
                        value = description.toString(),
                        onValueChange = {
                            description = it

                        },
                        placeholder = { Text("Description") },

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (validateFields()) {
                            onSave(
                                DebtModel(
                                    amount = amount.toString().toDouble(),
                                    description = description,
                                    createdAt = LocalDateTime.now().toString(),
                                    personId = -1
                                )
                            )
                            onDismiss()
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
        )

    }
}