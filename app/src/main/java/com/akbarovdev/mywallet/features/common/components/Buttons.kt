package com.akbarovdev.mywallet.features.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun DeleteButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
    }
}


@Composable
fun EditButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            Icons.Default.Edit, contentDescription = null, tint = Color.Green
        )
    }
}