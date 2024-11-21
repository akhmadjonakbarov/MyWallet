package com.akbarovdev.mywallt.utils

import android.icu.text.NumberFormat
import java.util.Locale

object NumberFormat {
    fun format(number: Double): String {
       return NumberFormat.getInstance(Locale("UZ")).format(number)
    }
}