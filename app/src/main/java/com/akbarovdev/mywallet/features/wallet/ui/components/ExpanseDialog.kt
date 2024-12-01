package com.akbarovdev.mywallet.features.wallet.ui.components


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
import androidx.compose.material.icons.outlined.*
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
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallet.utils.managers.IconManager
import java.time.LocalDateTime


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun ExpenseDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (ExpanseModel) -> Unit,
    expanseModel: ExpanseModel? = null
) {
    val measures = remember {
        mutableStateOf(
            mutableListOf(
                "kg", "qty"
            )
        )
    }


    var titleError by remember { mutableStateOf<String?>(null) }
    var qtyError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }



    if (isOpen) {

        var title by remember { mutableStateOf("") }
        var qty by remember { mutableStateOf("") }
        var price by remember {
            mutableStateOf("")
        }

        fun validateFields(): Boolean {
            titleError = if (title.isEmpty()) "Title cannot be empty" else null
            qtyError =
                if (qty.isEmpty() || qty.toDoubleOrNull() == null) "Amount must be a valid number" else null
            priceError =
                if (price.isEmpty() || price.toDoubleOrNull() == null) "Price must be a valid number" else null

            return titleError == null && qtyError == null && priceError == null
        }




        if (expanseModel != null) {
            title = expanseModel.title
            qty = expanseModel.qty.toString()
            price = expanseModel.price.toString()
        }


        val outlinedIcons = listOf(
            Icons.Outlined.Home,
            Icons.Outlined.Fastfood,
            Icons.Outlined.ShoppingBag,
            Icons.Outlined.Book,
            Icons.Outlined.Phone,
            Icons.Outlined.Coffee,
            Icons.Outlined.Settings,
            Icons.Outlined.Favorite,
            Icons.Outlined.AccountCircle,
            Icons.Outlined.Mail,
            Icons.Outlined.Phone,
            Icons.Outlined.Info,
            Icons.Outlined.Notifications,
            Icons.Outlined.LocationOn,
            Icons.Outlined.Search,
            Icons.Outlined.Help,
            Icons.Outlined.Share,
            Icons.Outlined.Security,
            Icons.Outlined.Language,
            Icons.Outlined.Lock,
            Icons.Outlined.Brightness7
            // Add more icons as needed
        )


        var selectedIcon by remember { mutableStateOf(Icons.Outlined.Home) }
        var selectedMeasure by remember { mutableStateOf("kg") }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = if (false) "Edit Expense" else "Add Expense")
            },
            text = {
                Column {
                    // Title TextField
                    TextField(
                        value = title,
                        onValueChange = {
                            title = it

                            titleError = if (it.isEmpty()) "Title cannot be empty" else null
                        },
                        label = { Text("Title") },
                        isError = titleError != null
                    )
                    if (titleError != null) {
                        Text(
                            text = titleError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Amount TextField
                    TextField(
                        value = qty.toString(),
                        onValueChange = {
                            qty = it
                            qtyError = if (it.isEmpty() || it.toDoubleOrNull() == null) {
                                "Amount must be a valid number"
                            } else {
                                null
                            }
                        },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = qtyError != null
                    )
                    if (qtyError != null) {
                        Text(
                            text = qtyError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

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
                        label = { Text("Price (Enter a product of price)") },
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

                    // Icons and Measures (keep as is)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(measures.value) { item ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    onClick = {
                                        selectedMeasure = item
                                    },
                                    selected = (selectedMeasure == item)
                                )
                                Text(item)
                            }
                        }
                    }

                    // Icon Picker
                    IconPicker(
                        icons = outlinedIcons,
                        selectedIcon = selectedIcon,
                        onIconSelected = { selectedIcon = it }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (validateFields()) {
                            onSave(
                                ExpanseModel(
                                    title = title,
                                    qty = qty.toDouble(),
                                    price = price.toDouble(),
                                    date = LocalDateTime.now().toString(),
                                    icon = IconManager.getIconName(selectedIcon),
                                    measure = selectedMeasure
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

@Composable
fun IconPicker(
    icons: List<ImageVector>, // List of icons to pick from
    selectedIcon: ImageVector?, // Currently selected icon
    onIconSelected: (ImageVector) -> Unit // Callback for icon selection
) {
    LazyRow {
        items(icons.size) { icon ->
            val icon = icons[icon]
            Row {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (icon == selectedIcon) Color.Blue else Color.LightGray)
                        .clickable { onIconSelected(icon) }
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (icon == selectedIcon) Color.White else Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

