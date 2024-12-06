package com.akbarovdev.mywallet.core.di

import com.akbarovdev.mywallet.core.database.WalletDatabase
import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.wallet.data.local.ExpanseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModules {
    @Provides
    @Singleton
    fun provideExpanseDao(db: WalletDatabase): ExpanseDao {
        return db.expanseDao()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(db: WalletDatabase): BudgetDao {
        return db.budgetDao()
    }


}