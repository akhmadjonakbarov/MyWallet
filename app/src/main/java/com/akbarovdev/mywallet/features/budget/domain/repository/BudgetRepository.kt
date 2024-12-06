package com.akbarovdev.mywallet.features.budget.domain.repository

import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun getBudgets(): Flow<List<BudgetModel>>
    suspend fun addBudget(budget: BudgetModel)
    suspend fun update(budget: BudgetModel)
    suspend fun delete(budget: BudgetModel)
}