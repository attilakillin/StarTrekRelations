package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.model.CharacterWithRelations
import hu.attilakillin.startrekrelations.network.CharacterBaseResponse
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: StarTrekApiService,
    private val dao: CharacterDao
) {
    suspend fun searchCharacters(
        name: String,
        pageNumber: Int
    ): CharacterBaseResponse = withContext(Dispatchers.IO) {
        service.searchCharacters(name = name, pageNumber = pageNumber)
    }

    fun loadDetails(uid: String) = flow<CharacterWithRelations> {

    }
}
