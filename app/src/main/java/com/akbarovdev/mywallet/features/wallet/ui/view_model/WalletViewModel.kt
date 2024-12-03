package com.akbarovdev.mywallet.features.wallet.ui.view_model

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.features.budget.domain.repository.BudgetRepository
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallet.features.wallet.domain.repositories.ExpanseRepository
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

    private val _state = MutableStateFlow(ExpanseState())
    val expanseState: StateFlow<ExpanseState> = _state.asStateFlow()


    fun addExpanse(expanse: ExpanseModel, onUpdate: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                allocateExpense(expanse)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to add expense: ${e.message}") }
            }

            onUpdate?.invoke()
        }
    }

    suspend fun allocateExpense(expense: ExpanseModel) {
        var remainingExpense = expense.price * expense.qty

        // Fetch budgets in a background-friendly manner
        val budgets = budgetRepository.getBudgets().first()

        budgets.forEach { budget ->
            if (remainingExpense <= 0) return@forEach

            // Calculate the deduction from the budget
            val deduction = minOf(remainingExpense, budget.remained)
            remainingExpense -= deduction

            if (deduction > 0) {
                // Only update the budget if there is a change in the remaining amount
                val updatedBudget = budget.copy(remained = budget.remained - deduction)
                budgetRepository.update(updatedBudget)

                // Create and add the updated expense
                val updatedExpense = expense.copy(budgetId = updatedBudget.id)
                expanseRepository.addExpense(updatedExpense)
            }
        }

        // If there’s remaining expense, throw an exception
        if (remainingExpense > 0) {
            throw IllegalStateException(
                "Insufficient budget to cover the expense of ${expense.price * expense.qty}. Remaining: $remainingExpense"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateExpanse(expanse: ExpanseModel) {
        viewModelScope.launch {
            try {
                val currentExpense = _state.value.currentExpanse

                if (currentExpense != null) {
                    val currentTotal = currentExpense.qty * currentExpense.price
                    val budgets = budgetRepository.getBudgets().first()

                    // Adjust the current budget if the expense was updated
                    budgets.find { it.id == currentExpense.budgetId }?.let { budget ->
                        val updatedBudget = budget.copy(remained = budget.remained + currentTotal)
                        budgetRepository.update(updatedBudget)
                    }

                    // Delete the old expense
                    expanseRepository.deleteExpense(currentExpense)

                    // Allocate the new expense
                    allocateExpense(expanse)
                }
            } catch (e: Exception) {
                handleError("Failed to update expense: ${e.message}")
            }
        }
    }


    @SuppressLint("NewApi")
    fun fetchExpanses() {
        viewModelScope.launch {
            try {
                expanseRepository.getExpenses().collect { expenses ->
                    val filteredExpanses = expenses.filter { expense ->
                        val expanseDate = LocalDateTime.parse(expense.date)
                        expanseDate.year == _selectedDate.value.year && expanseDate.month == _selectedDate.value.month && expanseDate.dayOfMonth == _selectedDate.value.dayOfMonth
                    }

                    // Update the state with filtered expenses
                    _state.update {
                        it.copy(
                            expanses = filteredExpanses, error = null
                        ) // Clear any previous errors
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to fetch expenses: ${e.message}") }
            }
        }
    }


    fun deleteExpanse(expanse: ExpanseModel) {
        viewModelScope.launch {
            expanseRepository.deleteExpense(expanse)
        }
    }

    fun selectExpanse(expanse: ExpanseModel) {
        _state.update { it.copy(currentExpanse = expanse) }
        openDialog()
    }

    fun handleError(e: String) {
        _state.update { it.copy(error = e) }
    }

    fun setDate(time: LocalDateTime) {
        _selectedDate.value = time
        fetchExpanses()
    }


}