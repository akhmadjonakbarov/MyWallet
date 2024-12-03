package com.akbarovdev.mywallet.features.wallet.ui.components

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
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import java.time.LocalDateTime


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun BudgetDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (BudgetModel) -> Unit,
) {


    var priceError by remember { mutableStateOf<String?>(null) }

    if (isOpen) {


        var price by remember {
            mutableStateOf("")
        }

        fun validateFields(): Boolean {

            priceError =
                if (price.isEmpty() || price.toDoubleOrNull() == null) "Price must be a valid number" else null

            return priceError == null
        }



        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Add Budget")
            },
            text = {
                Column {


                    // Price TextField
                    TextField(
                        value = price.toString(),
                        onValueChange = {
                            price = it
                            priceError = if (it.isEmpty() || it.toDoubleOrNull() == null) {
                                "Price must be a valid number"
                            } else {
                                null
                            }
                        },
                        label = { Text("Enter budget") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = priceError != null
                    )
                    if (priceError != null) {
                        Text(
                            text = priceError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (validateFields()) {
                            onSave(
                                BudgetModel(
                                    amount = price.toDouble(),
                                    date = LocalDateTime.now().toString(),
                                    remained = price.toDouble(),
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

