package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Brightness7
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.utils.IconManager
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
                Text(text = if (false) "Edit Expense" else "Add Budget")
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

