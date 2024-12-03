package com.akbarovdev.mywallet.features.profile.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.core.database.sharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    data class ProfileState(
        val username: String
    )

    private val _state = MutableStateFlow<ProfileState>(ProfileState(""))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    companion object {
        const val USERNAME_KEY = "username"
    }

    // Use the sharedPreferences delegate for username
    var username by context.sharedPreferences(USERNAME_KEY)

    init {
        getUsername()
    }

    // Update the username in shared preferences
    fun setName(newUsername: String) {
        viewModelScope.launch {
            username = newUsername // This updates shared preferences and the state
            _state.update { it.copy(newUsername) }
        }
    }

    // Fetch the username from shared preferences
    fun getUsername() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(username = username) }
            } catch (e: Exception) {
                // Handle error here
            }
        }
    }
}



