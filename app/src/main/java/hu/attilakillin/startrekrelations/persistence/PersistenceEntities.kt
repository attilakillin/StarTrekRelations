package hu.attilakillin.startrekrelations.persistence

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
 * The below entities are used to store a view of the model in the database.
 * At the bottom of the file, extension functions are provided to interface
 * with model entities.
 */

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

@Entity(foreignKeys = [
    ForeignKey(
        entity = Character::class,
        parentColumns = ["uid"],
        childColumns = ["sourceUid"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Relation(
    @PrimaryKey val id: Long,

    val sourceUid: String,
    val targetUid: String,
    val targetName: String,
    val type: String
)

data class CharacterWithRelations(
    @Embedded val character: Character,
    @androidx.room.Relation(
        parentColumn = "uid",
        entityColumn = "sourceUid"
    )
    val relations: List<Relation>
)

fun Character.toModel() = hu.attilakillin.startrekrelations.model.Character(
    uid = uid,
    name = name,

    species = species,
    gender = gender,
    yearOfBirth = yearOfBirth,
    yearOfDeath = yearOfDeath,
    weight = weight,
    height = height
)
