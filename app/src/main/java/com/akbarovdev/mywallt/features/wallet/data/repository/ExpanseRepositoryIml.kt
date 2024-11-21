package com.akbarovdev.mywallt.features.wallet.data.repository

import com.akbarovdev.mywallt.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.features.wallet.domain.repositories.ExpanseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpanseRepositoryIml @Inject constructor(
    private val expanseDao: ExpanseDao
) : ExpanseRepository {
    override suspend fun getExpenses(): Flow<List<ExpanseModel>> {
        return expanseDao.getContactsOrderByDate()
    }

    override suspend fun addExpense(expense: ExpanseModel) {
        expanseDao.insertExpanse(expense)
    }

    override suspend fun deleteExpense(expanse: ExpanseModel) {
         expanseDao.deleteExpanse(expanse)
    }
}