package com.akbarovdev.mywallet.features.budget.ui.view_model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import com.akbarovdev.mywallet.features.budget.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    data class BudgetState(
        val loading: Boolean = false,
        val lastAddedBudgetValue: Double = 0.0,
        val list: List<BudgetModel> = mutableListOf(),
        val budget: BudgetModel = BudgetModel(amount = 0.0, date = ""),
        val error: String? = null,
        val percentage: Double = 0.0
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
        fetchBudget()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchBudget() {
        viewModelScope.launch {

            repository.getBudgets().collect { budgets ->
                val date = LocalDateTime.now()


                if (budgets.isNotEmpty()) {
                    var totalAmount = 0.0
                    var totalRemained = 0.0
                    for (budget in budgets) {
                        if (LocalDateTime.parse(budget.date).month == date.month) {
                            totalRemained += budget.remained
                            totalAmount += budget.amount

                        }
                    }
                    val progressPercentage = if (totalAmount > 0) {
                        ((100 * (totalAmount - totalRemained)) / totalAmount)
                    } else {
                        0.0 // Avoid division by zero
                    }
                    val budget = BudgetModel(
                        remained = totalRemained,
                        amount = totalAmount,
                        date = date.toString()
                    )
                    Log.i("Budget", budget.toString())
                    _state.update {
                        it.copy(
                            budget = budget,
                            percentage = progressPercentage,
                            lastAddedBudgetValue = budgets.first().amount
                        )
                    }
                }
            }
        }
    }


    fun addBudget(budget: BudgetModel) {
        viewModelScope.launch {
            repository.addBudget(budget)
            fetchBudgets()
            fetchBudget()
        }
    }

    fun fetchBudgets() {
        viewModelScope.launch {
            try {
                repository.getBudgets().collect { budgets ->
                    _state.update {
                        it.copy(
                            list = budgets
                        )
                    }
                }

            } catch (e: Exception) {
                handleError(e)
            }
        }
    }


    fun handleError(e: Exception) {
        _state.update { it.copy(error = e.message) }
    }

}