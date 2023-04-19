package hu.attilakillin.startrekrelations.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResult(
    val uid: String,
    val name: String
)
