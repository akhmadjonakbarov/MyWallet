package com.akbarovdev.mywallet.features.debt.data.repository

import com.akbarovdev.mywallet.features.debt.data.local.PersonDao
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepositoryIml @Inject constructor(
    val dao: PersonDao
) : PersonRepository {
    override suspend fun getAll(): Flow<List<PersonModel>> {
        return dao.getAllPersons()
    }

    override suspend fun add(person: PersonModel) {
        dao.insert(person)
    }

    override suspend fun delete(person: PersonModel) {
        dao.delete(person)
    }
}