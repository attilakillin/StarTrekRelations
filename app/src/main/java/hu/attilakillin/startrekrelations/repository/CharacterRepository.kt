package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.PagedList
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.network.toModel
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import hu.attilakillin.startrekrelations.persistence.toEntity
import hu.attilakillin.startrekrelations.persistence.toModel
import kotlinx.coroutines.Dispatchers
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
        try {
            service.searchCharacters(name = name, pageNumber = pageNumber).toModel()
        } catch (ex: Exception) {
            PagedList(content = listOf(), pageNumber = 0, firstPage = true, lastPage = true)
        }
    }

    suspend fun addToFavorites(uid: String) = withContext(Dispatchers.IO) {
        // TODO Handle network errors here
        val characterEntity = try {
            service.getCharacterDetails(uid).toModel().toEntity()
        } catch (ex: Exception) {
            TODO()
        }

        dao.insertCharacterAndRelations(characterEntity.character, characterEntity.relations)
    }

    suspend fun removeFromFavorites(uid: String) = withContext(Dispatchers.IO) {
        dao.removeCharacter(uid)
    }

    suspend fun loadDetails(uid: String): Character = withContext(Dispatchers.IO) {
        if (dao.characterExists(uid)) {
            dao.loadCharacterDetails(uid).toModel()
        } else {
            // TODO Handle network errors
            service.getCharacterDetails(uid).toModel()
        }
    }
}
