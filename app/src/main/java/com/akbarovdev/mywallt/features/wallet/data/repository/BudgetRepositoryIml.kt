package com.akbarovdev.mywallt.features.wallet.data.repository

import com.akbarovdev.mywallt.features.wallet.data.local.BudgetDao
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.repositories.BudgetRepository
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
}