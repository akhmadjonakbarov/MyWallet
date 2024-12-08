package com.akbarovdev.mywallet.features.debt.data.repository

import com.akbarovdev.mywallet.features.debt.data.local.DebtDao
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import com.akbarovdev.mywallet.features.debt.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DebtRepositoryIml @Inject constructor(
    val dao: DebtDao
) : DebtRepository {
    override suspend fun getAll(): Flow<List<DebtModel>> {
        return dao.getAll()
    }

    override suspend fun add(debt: DebtModel) {
        dao.insert(debt)
    }



    override suspend fun delete(debt: DebtModel) {
        dao.delete(debt)
    }
}