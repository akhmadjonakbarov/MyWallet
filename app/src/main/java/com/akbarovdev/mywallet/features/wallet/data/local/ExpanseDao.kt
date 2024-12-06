package com.akbarovdev.mywallet.features.wallet.data.local

import androidx.room.Dao
import androidx.room.Delete

import androidx.room.Query
import androidx.room.Upsert
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpanseDao {

    @Upsert
    suspend fun insertExpanse(expanse: ExpanseModel)

    @Delete
    suspend fun deleteExpanse(expanse: ExpanseModel): Int

    @Query("SELECT * from expanses ORDER BY date DESC")
    fun getContactsOrderByDate(): Flow<List<ExpanseModel>>
}