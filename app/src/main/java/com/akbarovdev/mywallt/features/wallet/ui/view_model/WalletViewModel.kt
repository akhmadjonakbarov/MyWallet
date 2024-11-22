package com.akbarovdev.mywallt.features.wallet.ui.view_model

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.features.wallet.domain.repositories.BudgetRepository
import com.akbarovdev.mywallt.features.wallet.domain.repositories.ExpanseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val expanseRepository: ExpanseRepository, private val budgetRepository: BudgetRepository
) : ViewModel() {
    data class ExpanseState(
        val expanses: List<ExpanseModel> = emptyList(),
        val currentExpanse: ExpanseModel? = null,
        val error: String? = null // Optional: Handle errors
    )


    @SuppressLint("NewApi")
    private val _selectedDate = mutableStateOf(LocalDateTime.now())
    val selectedDate: State<LocalDateTime> = _selectedDate

    private val _isOpenDialog = mutableStateOf(false) // Tracks dialog state
    val isOpenDialog: State<Boolean> = _isOpenDialog

    fun openDialog() {
        _isOpenDialog.value = true
    }

    fun closeDialog() {
        _isOpenDialog.value = false
    }

    private val _expanseState = MutableStateFlow(ExpanseState())
    val expanseState: StateFlow<ExpanseState> = _expanseState.asStateFlow()

    init {
        fetchExpanses()
    }

    fun addExpanse(expanse: ExpanseModel, onUpdate: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                // Add expanse to the repository
                expanseRepository.addExpense(expanse)
                val budget: BudgetModel = budgetRepository.getBudgets().first().first()
                var remainedMoney = 0.0
                if (budget.remained > 0) {
                    remainedMoney = budget.remained - (expanse.qty * expanse.price)
                } else {
                    remainedMoney = budget.amount - (expanse.qty * expanse.price)
                }
                val updatedBudget = budget.copy(
                    remained = remainedMoney
                )
                budgetRepository.addBudget(updatedBudget)

            } catch (e: Exception) {
                _expanseState.update { it.copy(error = "Failed to add expense: ${e.message}") }
            }
            fetchExpanses()
            if (onUpdate != null) {
                onUpdate()
            }
        }
    }

    @SuppressLint("NewApi")
    fun fetchExpanses() {
        viewModelScope.launch {
            try {
                expanseRepository.getExpenses().collect { expanses ->
                    val filteredExpanses = expanses.filter {
                        val expanseDate = LocalDateTime.parse(it.date)
                        expanseDate.year == _selectedDate.value.year &&
                                expanseDate.month == _selectedDate.value.month &&
                                expanseDate.dayOfMonth == _selectedDate.value.dayOfMonth
                    }
                    _expanseState.update {
                        it.copy(expanses = filteredExpanses, error = null) // Clear previous errors
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

    fun setDate(time: LocalDateTime) {
        _selectedDate.value = time
        fetchExpanses()
    }


}