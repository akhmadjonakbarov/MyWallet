package com.akbarovdev.mywallet.utils

object FakeDataGenerator {
    fun number(border: Int): List<Float> {
        val data = mutableListOf<Float>()
        for (i in 1..border) {
            data.add((Math.random() * 100f).toFloat())
        }
        return data
    }

    fun intNumber(border: Int): List<Int> {
        val data = mutableListOf<Int>()
        for (i in 1..border) {
            data.add((Math.random() * 100f).toInt())
        }
        return data
    }

}