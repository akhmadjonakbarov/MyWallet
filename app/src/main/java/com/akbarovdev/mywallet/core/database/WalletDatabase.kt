package com.akbarovdev.mywallet.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel


@Database(entities = [ExpanseModel::class, BudgetModel::class], version = 2)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun expanseDao(): ExpanseDao
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getDatabase(context: Context): WalletDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDatabase::class.java,
                    "expanses"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
