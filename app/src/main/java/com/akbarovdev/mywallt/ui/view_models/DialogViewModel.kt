package com.akbarovdev.mywallt.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DialogViewModel @Inject constructor() : ViewModel() {
    private val _isDialogOpen = mutableStateOf(false)
    val isDialogOpen: State<Boolean> = _isDialogOpen
    fun openDialog() {
        _isDialogOpen.value = true
    }

    fun closeDialog() {
        _isDialogOpen.value = false
    }

}