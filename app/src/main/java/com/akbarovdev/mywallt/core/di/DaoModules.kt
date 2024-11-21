package com.akbarovdev.mywallt.core.di

import com.akbarovdev.mywallt.core.database.WalletDatabase
import com.akbarovdev.mywallt.features.wallet.data.local.ExpanseDao
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
    fun providerExpanseDao(db: WalletDatabase): ExpanseDao {
        return db.expanseDao()
    }
}