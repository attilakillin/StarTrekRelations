package hu.attilakillin.startrekrelations.persistence

import hu.attilakillin.startrekrelations.mocks.MockCharacterDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PersistenceEntitiesTests {
    private val source = MockCharacterDao()

    @Test
    fun character_toModel_works() = runBlocking {
        val response = source.searchCharacters("janeway").first()
        val janeway = response.toModel()

        assertEquals(janeway.uid, "CHMA00001")
        assertEquals(janeway.name, "Kathryn Janeway")
        assertEquals(janeway.gender, "F")
        assertEquals(janeway.species, "Human")
        assertEquals(janeway.yearOfBirth, null)
        assertEquals(janeway.yearOfDeath, null)
        assertEquals(janeway.weight, null)
        assertEquals(janeway.height, null)
        assertEquals(janeway.isFavorite, true)
    }

    @Test
    fun characterWithRelations_toModel_works() = runBlocking {
        val response = source.loadCharacterDetails("CHMA00001")
        val janeway = response.toModel()

        assertEquals(janeway.uid, "CHMA00001")
        assertEquals(janeway.name, "Kathryn Janeway")
        assertEquals(janeway.gender, "F")
        assertEquals(janeway.species, "Human")
        assertEquals(janeway.yearOfBirth, null)
        assertEquals(janeway.yearOfDeath, null)
        assertEquals(janeway.weight, null)
        assertEquals(janeway.height, null)
        assertEquals(janeway.isFavorite, true)

        assertEquals(janeway.relations.size, response.relations.size)
    }

    @Test
    fun characterModel_toEntity_works() = runBlocking {
        val model = source.loadCharacterDetails("CHMA00001").toModel()
        val entity = model.toEntity()

        assertEquals(entity.character.uid, "CHMA00001")
        assertEquals(entity.character.name, "Kathryn Janeway")
        assertEquals(entity.character.gender, "F")
        assertEquals(entity.character.species, "Human")
        assertEquals(entity.character.yearOfBirth, null)
        assertEquals(entity.character.yearOfDeath, null)
        assertEquals(entity.character.weight, null)
        assertEquals(entity.character.height, null)

        assertEquals(entity.relations.size, model.relations.size)
    }
}
