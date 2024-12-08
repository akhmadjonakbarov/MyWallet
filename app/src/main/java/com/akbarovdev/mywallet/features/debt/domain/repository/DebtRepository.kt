package com.akbarovdev.mywallet.features.debt.domain.repository

import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import kotlinx.coroutines.flow.Flow

interface DebtRepository {

    suspend fun getAll(): Flow<List<DebtModel>>
    suspend fun add(debt: DebtModel)
    suspend fun delete(debt: DebtModel)

}