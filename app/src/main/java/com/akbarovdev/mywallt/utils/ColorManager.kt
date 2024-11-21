package com.akbarovdev.mywallt.utils

import androidx.compose.ui.graphics.Color

object ColorManager {
    fun generate(colorCode: String): Color {
        return Color(color = "0xFF$colorCode".toLong(radix = 16).toInt())
    }
}