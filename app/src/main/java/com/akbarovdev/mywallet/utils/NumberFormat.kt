package com.akbarovdev.mywallet.utils

import android.icu.text.DecimalFormat
import android.os.Build

object NumberFormat {
    fun format(number: Double): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DecimalFormat("#,###").format(number)
        } else {
            number.toString()
        }
    }
}