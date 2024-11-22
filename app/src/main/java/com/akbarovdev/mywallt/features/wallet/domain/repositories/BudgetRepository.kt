package com.akbarovdev.mywallt.features.wallet.domain.repositories

import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun getBudgets(): Flow<List<BudgetModel>>
    suspend fun addBudget(budget: BudgetModel)
}