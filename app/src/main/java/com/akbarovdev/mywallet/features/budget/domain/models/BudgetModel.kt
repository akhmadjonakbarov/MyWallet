package com.akbarovdev.mywallet.features.budget.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "budgets",
)
data class BudgetModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val remained: Double = 0.0,
    val date: String
) {
    companion object {
        // Create a method that returns a default empty instance
        fun empty(): BudgetModel {
            return BudgetModel(
                id = -1,
                amount = 0.0,
                remained = 0.0,
                date = ""
            )
        }
    }
}
