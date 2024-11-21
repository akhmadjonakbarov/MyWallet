package com.akbarovdev.mywallt.features.wallet.domain.models

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
)