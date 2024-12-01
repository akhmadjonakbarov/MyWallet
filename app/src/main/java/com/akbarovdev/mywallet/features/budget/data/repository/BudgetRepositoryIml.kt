package com.akbarovdev.mywallet.features.budget.data.repository

import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import com.akbarovdev.mywallet.features.budget.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepositoryIml @Inject constructor(
    val dao: BudgetDao
) : BudgetRepository {
    override suspend fun getBudgets(): Flow<List<BudgetModel>> {
        return dao.getBudgetOrderByDate()
    }

    override suspend fun addBudget(budget: BudgetModel) {
        dao.insertBudget(budget)
    }

    override suspend fun update(budget: BudgetModel) {
        dao.updateBudget(budget)
    }
}