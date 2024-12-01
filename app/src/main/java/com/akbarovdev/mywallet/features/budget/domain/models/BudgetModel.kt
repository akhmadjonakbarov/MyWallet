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
)
