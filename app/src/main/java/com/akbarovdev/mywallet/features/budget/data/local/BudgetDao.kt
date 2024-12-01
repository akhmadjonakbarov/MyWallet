package com.akbarovdev.mywallet.features.budget.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Update
    suspend fun updateBudget(budget: BudgetModel)

    // Other DAO methods like insert, delete, etc.
    @Insert
    suspend fun insertBudget(budget: BudgetModel)

    @Delete
    suspend fun deleteBudget(budget: BudgetModel): Int

    @Query("SELECT * from budgets ORDER BY date DESC")
    fun getBudgetOrderByDate(): Flow<List<BudgetModel>>
}