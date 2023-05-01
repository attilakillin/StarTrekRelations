package hu.attilakillin.startrekrelations.model

data class Character(
    val uid: String,
    val name: String,

    val species: String? = null,
    val gender: String? = null,
    val yearOfBirth: Int? = null,
    val yearOfDeath: Int? = null,
    val weight: Int? = null,
    val height: Int? = null,

    val relations: List<Relation> = listOf(),

    var isFavorite: Boolean = false
)
