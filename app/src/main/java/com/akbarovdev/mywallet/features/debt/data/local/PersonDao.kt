package com.akbarovdev.mywallet.features.debt.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("select * from persons")
    fun getAllPersons(): Flow<List<PersonModel>>

    @Upsert
    suspend fun insert(person: PersonModel)

    @Delete
    suspend fun delete(person: PersonModel)
}