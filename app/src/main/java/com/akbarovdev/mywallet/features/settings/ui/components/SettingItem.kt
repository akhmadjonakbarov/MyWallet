package com.akbarovdev.mywallet.features.settings.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SettingItem(title: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(
                bottom = 8.dp
            )
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15)
            )
            .padding(
                15.dp
            )
    ) {
        Text(
            title, style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.W600
            )
        )
    }
}