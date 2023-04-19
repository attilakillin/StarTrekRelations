package hu.attilakillin.startrekrelations.model

import androidx.room.Embedded

data class CharacterWithRelations(
    @Embedded val character: Character,
    @androidx.room.Relation(
        parentColumn = "uid",
        entityColumn = "sourceUid"
    )
    val relations: List<Relation>
)
