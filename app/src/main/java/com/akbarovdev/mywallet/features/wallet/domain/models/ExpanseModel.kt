package com.akbarovdev.mywallet.features.wallet.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.akbarovdev.mywallet.features.budget.domain.models.BudgetModel


@Entity(
    tableName = "expanses",
    foreignKeys = [
        ForeignKey(
            entity = BudgetModel::class,
            parentColumns = ["id"],
            childColumns = ["budgetId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ExpanseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val price: Double,
    val qty: Double,
    val date: String,
    val icon: String,
    val measure: String,
    val budgetId: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpanseModel
        return id == other.id && title == other.title
    }

    override fun hashCode(): Int {
        return id.hashCode() * 31 + title.hashCode()
    }

    fun empty(): ExpanseModel {
        return ExpanseModel(
            id = -1,
            title = "",
            price = 0.0,
            qty = 0.0,
            date = "",
            icon = "",
            measure = "",
            budgetId = -1
        )
    }
}