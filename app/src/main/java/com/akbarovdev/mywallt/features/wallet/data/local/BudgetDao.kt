package com.akbarovdev.mywallt.features.wallet.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Upsert
    suspend fun insertBudget(budget: BudgetModel)

    @Delete
    suspend fun deleteBudget(budget: BudgetModel): Int

    @Query("SELECT * from budgets ORDER BY date DESC")
    fun getBudgetOrderByDate(): Flow<List<BudgetModel>>
}