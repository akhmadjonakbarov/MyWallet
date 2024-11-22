package com.akbarovdev.mywallt.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akbarovdev.mywallt.features.wallet.data.local.BudgetDao
import com.akbarovdev.mywallt.features.wallet.data.local.ExpanseDao
import com.akbarovdev.mywallt.features.wallet.domain.models.BudgetModel
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel


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
