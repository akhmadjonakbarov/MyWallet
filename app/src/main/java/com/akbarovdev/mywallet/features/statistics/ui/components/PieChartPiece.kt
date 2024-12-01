package com.akbarovdev.mywallet.features.statistics.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun PieChartPiece(
    percentage: Float,
    pieColor: Color
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            drawArc(
                color = pieColor,
                startAngle = 0f,
                sweepAngle = percentage,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(200f, 200f)
            )
        }) {
        Text("$percentage")
    }
}


@Preview
@Composable
fun PieChartPiecePreview() {
    PieChartPiece(percentage = 0.5f, pieColor = Color.Red)
}