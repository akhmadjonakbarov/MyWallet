package com.akbarovdev.mywallet.features.wallet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FloatButton(modifier: Modifier = Modifier, openDialog: () -> Unit) {
    SmallFloatingActionButton(
        modifier = modifier,
        onClick = {
            openDialog()
        }, containerColor = Color.Blue
    ) {
        Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color.White)
    }
}