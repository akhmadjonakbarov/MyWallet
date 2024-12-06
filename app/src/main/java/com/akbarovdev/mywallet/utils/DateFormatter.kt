package com.akbarovdev.mywallet.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    @SuppressLint("NewApi")
    fun format(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("MMMM, yyyy").format(date)
    }

    @SuppressLint("NewApi")
    fun formatWithDayHour(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm").format(date)
    }

    @SuppressLint("NewApi")
    fun formatWithDay(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date)
    }

    @SuppressLint("NewApi")
    fun formatWithoutMonthAndYear(date: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("d").format(date)
    }

}