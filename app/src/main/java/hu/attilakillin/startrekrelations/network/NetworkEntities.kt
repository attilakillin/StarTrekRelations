package hu.attilakillin.startrekrelations.network

import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.CharacterReference
import hu.attilakillin.startrekrelations.model.PagedList
import hu.attilakillin.startrekrelations.model.Relation
import kotlinx.serialization.Serializable

/*
 * The below entities are used to receive results from the STAPI network API.
 * At the bottom of the file, extension functions are provided to interface
 * with model entities.
 */

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

fun CharacterBaseResponse.toModel(): PagedList<Character> = PagedList(
    content = characters.map { it.toModel() },
    pageNumber = page.pageNumber,
    firstPage = page.firstPage,
    lastPage = page.lastPage
)

fun CharacterFullResponse.toModel(): Character = character.toModel()

fun CharacterBase.toModel(): Character = Character(
    uid = uid,
    name = name,

    gender = gender,
    yearOfBirth = yearOfBirth,
    yearOfDeath = yearOfDeath,
    weight = weight,
    height = height
)

fun CharacterFull.toModel(): Character = Character(
    uid = uid,
    name = name,

    species = characterSpecies.joinToString { it.name },
    gender = gender,
    yearOfBirth = yearOfBirth,
    yearOfDeath = yearOfDeath,
    weight = weight,
    height = height,

    relations = characterRelations.map { it.toModel() }
)

fun CharacterRelation.toModel(): Relation = Relation(
    qualifier = type,
    target = CharacterReference(
        uid = target.uid,
        name = target.name
    )
)
