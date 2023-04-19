package hu.attilakillin.startrekrelations.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Relation(
    @PrimaryKey val id: Long,

    val sourceUid: String,
    val targetUid: String,
    val targetName: String,
    val type: String
)
