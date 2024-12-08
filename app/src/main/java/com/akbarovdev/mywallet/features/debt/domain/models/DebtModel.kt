package com.akbarovdev.mywallet.features.debt.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.coroutines.internal.OpDescriptor
import kotlinx.serialization.Serializable

@Entity(
    tableName = "debts", foreignKeys = [ForeignKey(
        entity = PersonModel::class,
        parentColumns = ["id"],
        childColumns = ["personId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
@Serializable
data class DebtModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val personId: Int,
    val amount: Double,
    val isPaid: Boolean = false,
    val createdAt: String,
    val description: String = ""
) {
    companion object {
        fun empty(): DebtModel {
            return DebtModel(
                id = -1,
                personId = 0,
                amount = 0.0,
                isPaid = false,
                description = "",
                createdAt = ""
            )
        }
    }
}