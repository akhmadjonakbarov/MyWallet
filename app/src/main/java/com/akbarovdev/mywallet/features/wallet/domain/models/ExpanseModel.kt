package com.akbarovdev.mywallet.features.wallet.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "expanses"
)
data class ExpanseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val price: Double,
    val qty: Double,
    val date: String,
    val icon: String,
    val measure: String
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
}