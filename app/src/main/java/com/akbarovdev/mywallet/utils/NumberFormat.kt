package com.akbarovdev.mywallet.utils

import android.icu.text.DecimalFormat

object NumberFormat {
    fun format(number: Double): String {
        return DecimalFormat("#,###").format(number)
    }
}