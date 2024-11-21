package com.akbarovdev.mywallt.features.wallet.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.features.wallet.domain.repositories.ExpanseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val expanseRepository: ExpanseRepository
) : ViewModel() {
    data class ExpanseState(
        val expanses: List<ExpanseModel> = emptyList(),
        val currentExpanse: ExpanseModel? = null,
        val error: String? = null // Optional: Handle errors
    )

    private val _expanseState = MutableStateFlow(ExpanseState())
    val expanseState: StateFlow<ExpanseState> = _expanseState.asStateFlow()

    init {
        fetchExpanse()
    }

    fun addExpanse(expanse: ExpanseModel) {
        viewModelScope.launch {
            try {
                // Add expanse to the repository
                expanseRepository.addExpense(expanse)

                // Update state by adding the new expanse
                _expanseState.update {
                    it.copy(expanses = it.expanses + expanse)
                }
            } catch (e: Exception) {
                _expanseState.update { it.copy(error = "Failed to add expense: ${e.message}") }
            }
        }
    }

    fun fetchExpanse() {
        viewModelScope.launch {
            try {
                expanseRepository.getExpenses().collect { expanses ->
                    _expanseState.update {
                        it.copy(expanses = expanses, error = null) // Clear previous errors
                    }
                }
            } catch (e: Exception) {
                _expanseState.update { it.copy(error = "Failed to fetch expenses: ${e.message}") }
            }
        }
    }

    fun deleteExpanse(expanse: ExpanseModel) {
        viewModelScope.launch {
            expanseRepository.deleteExpense(expanse)
        }
    }

    fun selectExpanse(expanse: ExpanseModel) {
        _expanseState.update { it.copy(currentExpanse = expanse) }
    }

}