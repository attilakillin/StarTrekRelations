package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.CharacterWithRelations
import hu.attilakillin.startrekrelations.network.CharacterBase
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: StarTrekApiService,
    private val dao: CharacterDao
) {
    fun loadCharacters(name: String) = flow {
        emit(service.searchCharacters(name = name).characters)
    }.flowOn(Dispatchers.IO)

    fun loadDetails(uid: String) = flow<CharacterWithRelations> {

    }
}
