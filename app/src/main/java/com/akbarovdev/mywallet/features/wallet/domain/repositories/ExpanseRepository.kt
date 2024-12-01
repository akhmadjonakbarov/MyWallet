package com.akbarovdev.mywallet.features.wallet.domain.repositories

import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import kotlinx.coroutines.flow.Flow

interface ExpanseRepository {
    suspend fun getExpenses(): Flow<List<ExpanseModel>>
    suspend fun addExpense(expense: ExpanseModel)
    suspend fun deleteExpense(expanse: ExpanseModel)
}