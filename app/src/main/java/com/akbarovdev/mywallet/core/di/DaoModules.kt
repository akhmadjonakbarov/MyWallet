package com.akbarovdev.mywallet.core.di

import com.akbarovdev.mywallet.core.database.WalletDatabase
import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.debt.data.local.DebtDao
import com.akbarovdev.mywallet.features.debt.data.local.PersonDao
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

    @Provides
    @Singleton
    fun providePersonDao(db: WalletDatabase): PersonDao {
        return db.personDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: WalletDatabase): DebtDao {
        return db.debtDao()
    }


}