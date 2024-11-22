package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("DefaultLocale")
@Composable
fun ProgressBar(amount: Double, remained: Double, configuration: Configuration) {
    val percent = if (remained > 0) (100 * (amount - remained)) / amount else 0.0
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .background(Color.LightGray, shape = CircleShape)

        ) {
            Spacer(
                Modifier.height(15.dp)
            )

        }
        UsedMoneyProgress(percent, configuration)
        ShowPercentage(percent)
    }
}

@Composable
fun UsedMoneyProgress(percent: Double, configuration: Configuration) {
    Box(
        modifier = Modifier
            .width((configuration.screenWidthDp * percent / 100).dp)
            .padding(horizontal = 5.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 0f) // Horizontal direction
                ),
                shape = CircleShape
            )

    ) {
        Spacer(
            Modifier.height(15.dp)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ShowPercentage(percent: Double) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${String.format("%.2f", percent)}%",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.W600,
                color = if (percent < 50) Color.Black else Color.White
            )
        )
    }
}