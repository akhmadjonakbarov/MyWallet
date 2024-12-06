package com.akbarovdev.mywallet.features.wallet.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(icon: @Composable () -> Unit, onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.border(
            width = 1.dp, color = Color.Black, shape = CircleShape
        ), onClick = {
            onClick()
        }
    ) {
        icon()
    }
}

@Preview
@Composable
fun PreviewIconBtn() {
    CircleButton(icon = {
        Icon(
            Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = null
        )
    }) { /* Click action */ }
}