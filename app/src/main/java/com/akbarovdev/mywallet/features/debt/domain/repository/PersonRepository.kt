package com.akbarovdev.mywallet.features.debt.domain.repository

import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getAll(): Flow<List<PersonModel>>
    suspend fun add(person: PersonModel)
    suspend fun delete(person: PersonModel)
}