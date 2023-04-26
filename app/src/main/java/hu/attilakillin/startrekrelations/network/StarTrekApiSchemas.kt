package hu.attilakillin.startrekrelations.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterBaseResponse(
    val page: ResponsePage,
    val characters: List<CharacterBase>
)

@Serializable
data class CharacterFullResponse(
    val character: CharacterFull
)

@Serializable
data class ResponsePage(
    val pageNumber: Int,
    val pageSize: Int,
    val numberOfElements: Int,
    val totalElements: Int,
    val totalPages: Int,
    val firstPage: Boolean,
    val lastPage: Boolean
)

@Serializable
data class CharacterBase(
    val uid: String,
    val name: String,
    val gender: String?,
    val yearOfBirth: Int?,
    val yearOfDeath: Int?,
    val height: Int?,
    val weight: Int?
)

@Serializable
data class CharacterFull(
    val uid: String,
    val name: String,
    val gender: String?,
    val yearOfBirth: Int?,
    val yearOfDeath: Int?,
    val height: Int?,
    val weight: Int?,
    val characterSpecies: List<CharacterSpecies>,
    val characterRelations: List<CharacterRelation>
)

@Serializable
data class CharacterSpecies(
    val uid: String,
    val name: String,
    val numerator: Int,
    val denominator: Int
)

@Serializable
data class CharacterRelation(
    val type: String,
    val source: CharacterHeader,
    val target: CharacterHeader
)

@Serializable
data class CharacterHeader(
    val uid: String,
    val name: String
)
