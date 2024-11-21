package com.akbarovdev.mywallt.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    @SuppressLint("NewApi")
    fun format(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("MMMM, yyyy").format(date)
    }

    @SuppressLint("NewApi")
    fun formatWithDay(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm").format(date)
    }

}