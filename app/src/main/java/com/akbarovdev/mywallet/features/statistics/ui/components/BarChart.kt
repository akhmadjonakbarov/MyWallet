package com.akbarovdev.mywallet.features.statistics.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.statistics.domain.models.DailyExpanseChartModel
import com.akbarovdev.mywallet.utils.DateFormatter
import com.akbarovdev.mywallet.utils.NumberFormat
import java.time.LocalDateTime
import kotlin.random.Random

@SuppressLint("NewApi")
@Composable
fun BarChart(
    charts: List<DailyExpanseChartModel>, height: Double,
) {


    var rateOfPrices = remember { mutableStateOf(mutableListOf<Double>()) }
    val paddingOfEachBar = 30

    fun calculateRate() {
        if (charts.isEmpty()) return

        val maxPrice = charts.maxOf { it.price }
        val minPrice = charts.minOf { it.price }

        // Generate evenly spaced rates based on the range
        val step = (maxPrice - minPrice) / 4.0 // 4 intervals
        val rates = mutableListOf<Double>()

        for (i in 0..4) {
            rates.add(minPrice + i * step)
        }


        rateOfPrices.value = rates.sortedByDescending { it }.toMutableList()
    }


    LaunchedEffect(Unit) {
        calculateRate()
    }


    Column(
        Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(height.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(DateFormatter.format(LocalDateTime.parse(charts.first().date)))
            Text(
                "SO'M",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        Row {
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                for (i in rateOfPrices.value.indices) {
                    Text(NumberFormat.format(rateOfPrices.value[i]))
                }
            }

            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val gridColor = Color.Gray
                    val gridLineWidth = 1.dp.toPx()

                    // Calculate the spacing for grid lines
                    // Calculate the spacing for grid lines
                    val verticalSpacing = size.width / (charts.size + 1) // Dynamic grid lines
                    val horizontalSpacing = size.height / (rateOfPrices.value.size - 1)

                    // Draw vertical grid lines
                    // Draw vertical grid lines
                    for (i in 0..charts.size) {
                        val x = i * verticalSpacing
                        drawLine(
                            color = gridColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = gridLineWidth
                        )
                    }

                    // Draw horizontal grid lines
                    for (i in rateOfPrices.value.indices) {
                        val y = i * horizontalSpacing
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = gridLineWidth
                        )
                    }
                }
                .height(225.dp)) {

                items(charts.count()) { index ->
                    val chartItem = charts[index]

                    // Calculate the height of the bar based on the value
                    val maxHeight =
                        with(LocalDensity.current) { 180.dp.toPx() } // Maximum height of the bar
                    val maxValue = charts.maxOf { it.price } // Maximum value in the dataset
                    val barHeight = (chartItem.price / maxValue * maxHeight).toFloat()

                    BarChartItem(chartItem, paddingOfEachBar, barHeight)
                }
            }
        }
    }
}

