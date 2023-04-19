package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.CharacterWithRelations
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: StarTrekApiService,
    private val dao: CharacterDao
) {

    fun loadCharacters() = flow<List<Character>> {

    }

    fun loadDetails(uid: String) = flow<CharacterWithRelations> {

    }
}
