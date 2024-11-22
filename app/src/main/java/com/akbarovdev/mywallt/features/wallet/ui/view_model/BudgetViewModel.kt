package com.akbarovdev.mywallt.features.wallet.ui.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.repositories.BudgetRepository
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
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    data class BudgetState(
        val budget: BudgetModel = BudgetModel(amount = 0.0, date = ""),
        val error: String? = null,
    )

    private val _state = MutableStateFlow(BudgetState())
    val state: StateFlow<BudgetState> = _state.asStateFlow()

    private val _isOpenDialog = mutableStateOf(false) // Tracks dialog state
    val isOpenDialog: State<Boolean> = _isOpenDialog

    fun openDialog() {
        _isOpenDialog.value = true
    }

    fun closeDialog() {
        _isOpenDialog.value = false
    }


    init {
        fetchBudgets()
    }

    fun fetchBudgets() {
        viewModelScope.launch {
            Log.i("Budgets", "GET ALL")

            val budgets = repository.getBudgets().first()
            if (budgets.isNotEmpty()) {
                Log.i("Budgets", "${budgets.first()}")
                _state.update {
                    it.copy(
                        budget = budgets.first()
                    )
                }
            }
        }
    }

    fun addBudget(budget: BudgetModel) {
        viewModelScope.launch {

            fetchBudgets()


            val updatedBudget = _state.value.budget.amount.takeIf { it > 0 }?.let {
                budget.copy(amount = budget.amount + it)
            } ?: budget


            repository.addBudget(updatedBudget)


            fetchBudgets()
        }
    }


}