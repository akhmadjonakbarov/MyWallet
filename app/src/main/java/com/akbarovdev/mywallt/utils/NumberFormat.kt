package com.akbarovdev.mywallt.utils

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import java.util.Locale

object NumberFormat {
    fun format(number: Double): String {
        return DecimalFormat("#,###").format(number)
    }
}