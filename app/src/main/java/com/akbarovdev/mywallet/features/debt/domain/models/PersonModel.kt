package com.akbarovdev.mywallet.features.debt.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*
import kotlinx.serialization.json.*


@Entity(
    tableName = "persons"
)
@Serializable
data class PersonModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phoneNumber: String,
) {
    companion object {
        fun empty(): PersonModel {
            return PersonModel(
                id = -1,
                name = "",
                phoneNumber = ""
            )
        }
    }
}