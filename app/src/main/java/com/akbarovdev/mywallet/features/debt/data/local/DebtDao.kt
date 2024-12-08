package com.akbarovdev.mywallet.features.debt.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {


    @Query("select * from debts")
    fun getAll(): Flow<List<DebtModel>>

    @Upsert
    suspend fun insert(debt: DebtModel)


    @Delete
    suspend fun delete(debt: DebtModel)

}