package com.akbarovdev.mywallt.core.di

import android.content.Context
import com.akbarovdev.mywallt.core.database.WalletDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun providerWalletDatabase(
        @ApplicationContext context: Context
    ): WalletDatabase {
        return WalletDatabase.getDatabase(context)
    }
}