package com.akbarovdev.mywallet.utils.managers

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object ColorManager {
    fun regenerate(colorCode: String): Color {
        return Color(color = "0xFF$colorCode".toLong(radix = 16).toInt())
    }

    fun generate(count: Int): List<Color> {
        var colors = mutableListOf<Color>()
        for (i in 1..count) {
            val random = Random.Default
            val r = random.nextInt(102, 256) // High red value (128–255)
            val g = random.nextInt(51, 156) // High green value (128–255)
            val b = random.nextInt(100, 256) // High blue value (128–255)
            if (r != 255 && g != 255 && b != 255) {
                colors.add(Color(r, g, b))
            }
        }
        return colors
    }
}