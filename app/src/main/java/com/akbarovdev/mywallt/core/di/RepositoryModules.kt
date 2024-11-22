package com.akbarovdev.mywallt.core.di

import com.akbarovdev.mywallt.features.wallet.data.local.BudgetDao
import com.akbarovdev.mywallt.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallt.features.wallet.data.repository.BudgetRepositoryIml
import com.akbarovdev.mywallt.features.wallet.data.repository.ExpanseRepositoryIml
import com.akbarovdev.mywallt.features.wallet.domain.repositories.BudgetRepository
import com.akbarovdev.mywallt.features.wallet.domain.repositories.ExpanseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {
    @Provides
    @Singleton
    fun provideExpanseRepository(dao: ExpanseDao): ExpanseRepository {
        return ExpanseRepositoryIml(dao)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(dao: BudgetDao): BudgetRepository {
        return BudgetRepositoryIml(dao)
    }
}