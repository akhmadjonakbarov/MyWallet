package com.akbarovdev.mywallet.utils

object StringChecker {
    private fun normalize(input: String, keepSymbols: Boolean = false): String {
        require(input.isNotBlank()) { "Input string cannot be blank" }
        return input.lowercase()
            .let {
                if (keepSymbols) it.replace("[^a-zA-Z0-9'-]".toRegex(), "")
                else it.replace("[^a-zA-Z0-9]".toRegex(), "")
            }
            .trim()
    }

    fun areSimilar(
        str1: String,
        str2: String,
        ignoreCase: Boolean = true,
        ignoreSymbols: Boolean = true
    ): Boolean {
        val normalizedStr1 = normalize(str1, keepSymbols = !ignoreSymbols)
        val normalizedStr2 = normalize(str2, keepSymbols = !ignoreSymbols)
        return if (ignoreCase) normalizedStr1 == normalizedStr2
        else str1 == str2
    }
}
