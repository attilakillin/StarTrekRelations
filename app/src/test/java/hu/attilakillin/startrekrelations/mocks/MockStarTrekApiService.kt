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

class MockStarTrekApiService : StarTrekApiService {
    override suspend fun searchCharacters(
        name: String,
        pageNumber: Int,
        pageSize: Int
    ): CharacterBaseResponse = CharacterBaseResponse(
        page = ResponsePage(
            pageNumber = 0,
            pageSize = 2,
            numberOfElements = 2,
            totalElements = 2,
            totalPages = 1,
            firstPage = true,
            lastPage = true
        ),
        characters = listOf(
            CharacterBase(
                uid = "CHMA00001", name = "Kathryn Janeway", gender = "F", yearOfBirth = null,
                yearOfDeath = null, weight = null, height = null
            ),
            CharacterBase(
                uid = "CHMA00002", name = "Spock", gender = "M", yearOfBirth = null,
                yearOfDeath = null, weight = null, height = null
            )
        )
    )

    override suspend fun getCharacterDetails(uid: String): CharacterFullResponse {
        return CharacterFullResponse(
            character = CharacterFull(
                uid = "CHMA00001", name = "Kathryn Janeway", gender = "F", yearOfBirth = null,
                yearOfDeath = null, weight = null, height = null,
                characterSpecies = listOf(CharacterSpecies(uid = "CS0", name = "Human", numerator = 1, denominator = 1)),
                characterRelations = listOf(CharacterRelation(
                    type = "Good friend", source = CharacterHeader(uid = "CHMA00001", name = "Kathryn Janeway"),
                    target = CharacterHeader(uid = "CHMA00002", name = "Spock")
                ))
            )
        )
    }
}