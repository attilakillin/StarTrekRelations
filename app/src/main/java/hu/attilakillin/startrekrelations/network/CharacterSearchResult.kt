package hu.attilakillin.startrekrelations.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterSearchResult(
    val characters: List<CharacterResult>
)
