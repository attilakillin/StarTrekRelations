package hu.attilakillin.startrekrelations.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val uid: String,

    val name: String,
    val species: String?,
    val gender: String?,
    val yearOfBirth: Int?,
    val yearOfDeath: Int?,
    val weight: Int?,
    val height: Int?
)
