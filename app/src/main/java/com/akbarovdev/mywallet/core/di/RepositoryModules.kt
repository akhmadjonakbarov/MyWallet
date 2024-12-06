package com.akbarovdev.mywallet.core.di

import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallet.features.budget.data.repository.BudgetRepositoryIml
import com.akbarovdev.mywallet.features.wallet.data.repository.ExpanseRepositoryIml
import com.akbarovdev.mywallet.features.budget.domain.repository.BudgetRepository
import com.akbarovdev.mywallet.features.wallet.domain.repositories.ExpanseRepository
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