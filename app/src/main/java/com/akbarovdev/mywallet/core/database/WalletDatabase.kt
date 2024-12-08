package com.akbarovdev.mywallet.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akbarovdev.mywallet.features.budget.data.local.BudgetDao
import com.akbarovdev.mywallet.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel
import com.akbarovdev.mywallet.features.debt.data.local.DebtDao
import com.akbarovdev.mywallet.features.debt.data.local.PersonDao
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel


@Database(
    entities = [
        ExpanseModel::class, BudgetModel::class,
        PersonModel::class, DebtModel::class
    ],
    version = 4
)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun expanseDao(): ExpanseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun personDao(): PersonDao
    abstract fun debtDao(): DebtDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getDatabase(context: Context): WalletDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDatabase::class.java,
                    "wallet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
