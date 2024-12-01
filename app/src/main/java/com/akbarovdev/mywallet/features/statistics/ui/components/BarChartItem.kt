package com.akbarovdev.mywallet.features.statistics.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.statistics.domain.models.DailyExpanseChartModel
import com.akbarovdev.mywallet.utils.DateFormatter
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BarChartItem(chartItem: DailyExpanseChartModel, paddingOfEachBar: Int, barHeight: Float) {
    Column(
        modifier = Modifier
            .padding()
            .width(paddingOfEachBar.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = (paddingOfEachBar / 2).dp)
                .width(paddingOfEachBar.dp)
                .height(192.dp)
                .drawBehind {
                    drawLine(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Red, Color.Green, Color.Blue
                            ),
                        ),
                        strokeWidth = 15f,
                        end = Offset(
                            0f, size.height - barHeight
                        ), // Height of the bar
                        start = Offset(
                            0f, size.height
                        ),
                    )
                }, contentAlignment = Alignment.Center
        ) {

        }
        Text(
            modifier = Modifier
                .width(paddingOfEachBar.dp)
                .align(Alignment.CenterHorizontally),
            text = DateFormatter.formatWithoutMonthAndYear(LocalDateTime.parse(chartItem.date)), // Correctly extract day
            textAlign = TextAlign.Center
        )
    }
}