package com.akbarovdev.mywallet.features.statistics.ui.view_model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.features.statistics.domain.models.DailyExpanseChartModel
import com.akbarovdev.mywallet.features.statistics.domain.models.StatisticModel
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallet.features.wallet.domain.repositories.ExpanseRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val expanseRepository: ExpanseRepository
) : ViewModel() {

    data class StatisticState(
        val statistics: List<StatisticModel> = emptyList(),
        val charts: List<DailyExpanseChartModel> = emptyList<DailyExpanseChartModel>(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(StatisticState())
    val state: StateFlow<StatisticState> = _state.asStateFlow()

    init {
        getStatistics()
    }

    private fun setIsLoading(value: Boolean = true) {
        _state.update { it.copy(isLoading = value) }
    }

    fun getStatistics() {
        viewModelScope.launch {
            try {
                setIsLoading()
                val expenses = getExpanses()
                val charts = getDailyExpanseChart(expenses)
                val mostBoughtExpanses = getMostBought(expenses)
                val mostExpansiveExpanses = getMostExpensive()
                val statistics = listOf(
                    StatisticModel(
                        label = "Most Bought",
                        list = mostBoughtExpanses
                    ),
                    StatisticModel(
                        label = "Most Expansive",
                        list = mostExpansiveExpanses
                    )
                )

                _state.update {
                    it.copy(
                        charts = charts,
                        statistics = statistics,
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to fetch expenses: ${e.message}") }
                Log.e("ViewModel", "Error fetching statistics: ${e.message}")
            } finally {
                setIsLoading(false)
            }
        }
    }


    @SuppressLint("NewApi")
    private fun getDailyExpanseChart(expenses: List<ExpanseModel>): List<DailyExpanseChartModel> {
        try {
            // Get the current date and extract the current month & year
            val currentDate = LocalDate.now()
            val currentMonth = currentDate.month
            val currentYear = currentDate.year

            // Create a map to hold daily totals
            val dailyExpenseMap =
                mutableMapOf<Int, Double>() // Key: Day of Month, Value: Total Expense

            for (expanse in expenses) {
                // Parse the expense date
                val expenseDate = LocalDateTime.parse(expanse.date)

                // Check if the expense belongs to the current month and year
                if (expenseDate.month == currentMonth && expenseDate.year == currentYear) {
                    // Calculate total price for the expense
                    val totalPriceForExpanse = expanse.price * expanse.qty

                    // Add it to the corresponding day in the map
                    dailyExpenseMap[expenseDate.dayOfMonth] = dailyExpenseMap.getOrDefault(
                        expenseDate.dayOfMonth, 0.0
                    ) + totalPriceForExpanse
                }
            }

            // Convert the map into a list of DailyExpanseChartModel
            return dailyExpenseMap.map { (day, totalExpense) ->
                DailyExpanseChartModel(
                    date = LocalDateTime.of(currentYear, currentMonth, day, 0, 0, 0).toString(),
                    price = totalExpense
                )
            }

        } catch (e: Exception) {
            throw e
        }
    }


    private fun getMostBought(expenses: List<ExpanseModel>): MutableList<ExpanseModel> {
        val expenseMap = mutableMapOf<String, ExpanseModel>()

        for (expense in expenses) {
            val key = expense.title // Use `name` or a unique identifier for grouping
            if (key in expenseMap) {
                // Update the quantity for the existing expense
                val existingExpense = expenseMap[key]!!
                expenseMap[key] = existingExpense.copy(qty = existingExpense.qty + expense.qty)
            } else {
                // Add the new expense to the map
                expenseMap[key] = expense
            }
        }

        // Convert the map values to a list, sort by quantity, and return
        return expenseMap.values.sortedByDescending { it.qty }.toMutableList()
    }

    private suspend fun getMostExpensive(): MutableList<ExpanseModel> {
        val expenses = getExpanses()
        val mostBought = getMostBought(expenses)
        val mostExpensive = mostBought.sortedByDescending { it.price * it.qty }
        return mostExpensive.toMutableList()
    }


    private suspend fun getExpanses(): List<ExpanseModel> {
        return try {
            expanseRepository.getExpenses().first()
        } catch (e: Exception) {
            throw e
        }
    }
}
