package com.akbarovdev.mywallet.features.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AlertTextBox(title: String, textColor: Color = Color.Black, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center // Centers the content in the Box
    ) {
        Text(
            title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = textColor
            )
        )
    }
}