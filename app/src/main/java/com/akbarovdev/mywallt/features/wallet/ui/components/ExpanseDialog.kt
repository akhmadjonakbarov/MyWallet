package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.utils.IconManager
import java.time.LocalDate

import java.time.LocalDateTime


@SuppressLint("NewApi")
@Composable
fun ExpenseDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (ExpanseModel) -> Unit,
    expanseModel: ExpanseModel? = null
) {
    if (isOpen) {

        var title by remember { mutableStateOf("") }
        var qty by remember { mutableStateOf("") }
        var price by remember {
            mutableStateOf("")
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
        AlertDialog(onDismissRequest = onDismiss, title = {
            Text(text = if (false) "Edit Expense" else "Add Expense")
        }, text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = qty.toString(),
                    onValueChange = { qty = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = price.toString(),
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                // Sample icons
                Spacer(modifier = Modifier.height(8.dp))

                IconPicker(icons = outlinedIcons,
                    selectedIcon = selectedIcon,
                    onIconSelected = { selectedIcon = it }
                )
            }
        }, confirmButton = {
            Button(onClick = {
                onSave(
                    ExpanseModel(
                        title = title, qty = qty.toDouble(),
                        price = price.toDouble(),
                        date = LocalDateTime.now().toString(),
                        icon = IconManager.getIconName(selectedIcon), measure = ""
                    )
                )
                onDismiss()
            }) {
                Text("Save")
            }
        }, dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        })
    }
}

@Composable
fun IconPicker(
    icons: List<ImageVector>, // List of icons to pick from
    selectedIcon: ImageVector?, // Currently selected icon
    onIconSelected: (ImageVector) -> Unit // Callback for icon selection
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5), // Number of columns
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(icons) { icon ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (icon == selectedIcon) Color.Blue else Color.LightGray)
                    .clickable { onIconSelected(icon) }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (icon == selectedIcon) Color.White else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

