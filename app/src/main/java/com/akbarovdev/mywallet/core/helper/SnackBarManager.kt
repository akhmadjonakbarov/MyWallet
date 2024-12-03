package com.akbarovdev.mywallet.core.helper

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarManager(
    val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    var snackBarColor by mutableStateOf(Color.DarkGray)
    fun showSnackBar(message: String, isAlert: Boolean = false) {
        snackBarColor = if (isAlert) Color.Red else Color.DarkGray
        snackBarHostState.currentSnackbarData?.dismiss()
        scope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
}