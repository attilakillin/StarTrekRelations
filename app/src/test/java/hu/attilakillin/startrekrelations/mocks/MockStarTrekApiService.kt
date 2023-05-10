package hu.attilakillin.startrekrelations.mocks

import hu.attilakillin.startrekrelations.network.CharacterBase
import hu.attilakillin.startrekrelations.network.CharacterBaseResponse
import hu.attilakillin.startrekrelations.network.CharacterFull
import hu.attilakillin.startrekrelations.network.CharacterFullResponse
import hu.attilakillin.startrekrelations.network.CharacterHeader
import hu.attilakillin.startrekrelations.network.CharacterRelation
import hu.attilakillin.startrekrelations.network.CharacterSpecies
import hu.attilakillin.startrekrelations.network.ResponsePage
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class MockStarTrekApiService : StarTrekApiService {
    override suspend fun searchCharacters(
        name: String,
        pageNumber: Int,
        pageSize: Int
    ): CharacterBaseResponse {
        val characters = listOf(
            CharacterBase(
                uid = "CHMA00001", name = "Kathryn Janeway", gender = "F", yearOfBirth = null,
                yearOfDeath = null, weight = null, height = null
            ),
            CharacterBase(
                uid = "CHMA00002", name = "Spock", gender = "M", yearOfBirth = null,
                yearOfDeath = null, weight = null, height = null
            )
        )

        val result = characters.filter { it.name.lowercase().contains(name.lowercase()) }

        return CharacterBaseResponse(
            page = ResponsePage(
                pageNumber = 0,
                pageSize = result.size,
                numberOfElements = result.size,
                totalElements = result.size,
                totalPages = 1,
                firstPage = true,
                lastPage = true
            ),
            characters = result
        )
    }

    override suspend fun getCharacterDetails(uid: String): CharacterFullResponse {
        val janeway = CharacterFull(
            uid = "CHMA00001", name = "Kathryn Janeway", gender = "F", yearOfBirth = null,
            yearOfDeath = null, weight = null, height = null,
            characterSpecies = listOf(CharacterSpecies(uid = "CS0", name = "Human", numerator = 1, denominator = 1)),
            characterRelations = listOf(CharacterRelation(
                type = "Good friend", source = CharacterHeader(uid = "CHMA00001", name = "Kathryn Janeway"),
                target = CharacterHeader(uid = "CHMA00002", name = "Spock")
            ))
        )

        val spock = CharacterFull(
            uid = "CHMA00002", name = "Spock", gender = "M", yearOfBirth = null,
            yearOfDeath = null, weight = null, height = null,
            characterSpecies = listOf(
                CharacterSpecies(uid = "CS0", name = "Human", numerator = 1, denominator = 2),
                CharacterSpecies(uid = "CS1", name = "Vulcan", numerator = 1, denominator = 2)
            ),
            characterRelations = listOf(CharacterRelation(
                type = "Good friend", source = CharacterHeader(uid = "CHMA00002", name = "Spock"),
                target = CharacterHeader(uid = "CHMA00001", name = "Kathryn Janeway")
            ))
        )

        return when (uid) {
            "CHMA00001" -> CharacterFullResponse(character = janeway)
            "CHMA00002" -> CharacterFullResponse(character = spock)
            else -> throw HttpException(Response.error<Unit>(404, ResponseBody.create(null, "")))
        }
    }
}
