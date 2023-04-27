package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.PagedList
import hu.attilakillin.startrekrelations.persistence.CharacterWithRelations
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.network.toModel
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import hu.attilakillin.startrekrelations.persistence.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: StarTrekApiService,
    private val dao: CharacterDao
) {
    suspend fun searchFavorites(
        name: String
    ): List<Character> = withContext(Dispatchers.IO) {
        dao.searchCharacters(name).map { it.toModel() }
    }

    suspend fun searchCharacters(
        name: String,
        pageNumber: Int
    ): PagedList<Character> = withContext(Dispatchers.IO) {
        service.searchCharacters(name = name, pageNumber = pageNumber).toModel()
    }

    fun loadDetails(uid: String) = flow<CharacterWithRelations> {

    }
}
