package hu.attilakillin.startrekrelations.repository

import hu.attilakillin.startrekrelations.mocks.MockCharacterDao
import hu.attilakillin.startrekrelations.mocks.MockStarTrekApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CharacterRepositoryTests {
    val network = MockStarTrekApiService()
    val dao = MockCharacterDao()
    val repository = CharacterRepository(network, dao)

    @Test
    fun searchFavorites_works() = runBlocking {
        val results = repository.searchFavorites("janeway")
        val janeway = results.first()

        assertEquals(results.size, 1)

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
    fun searchFavorites_withEmptyResults_works() = runBlocking {
        val results = repository.searchFavorites("no such character name")

        assertEquals(results.size, 0)
    }

    @Test
    fun searchCharacters_works() = runBlocking {
        val results = repository.searchCharacters("janeway", 0).content
        val janeway = results.first()

        assertEquals(results.size, 1)

        assertEquals(janeway.uid, "CHMA00001")
        assertEquals(janeway.name, "Kathryn Janeway")
        assertEquals(janeway.gender, "F")
        assertEquals(janeway.yearOfBirth, null)
        assertEquals(janeway.yearOfDeath, null)
        assertEquals(janeway.weight, null)
        assertEquals(janeway.height, null)
    }

    @Test
    fun searchCharacters_favoriteHandling_works() = runBlocking {
        val results = repository.searchCharacters("", 0).content

        assertEquals(results.size, 2)

        assertEquals(results.first().isFavorite, true)
        assertEquals(results.last().isFavorite, true)
    }

    @Test
    fun addToFavorites_works() = runBlocking {
        val before = dao.loadCharacterUids().size
        val result = repository.addToFavorites("CHMA00001")
        val after = dao.loadCharacterUids().size

        assertTrue(result)
        assertEquals(before + 1, after)
    }

    @Test
    fun addToFavorites_wrongId_works() {
        val before = dao.loadCharacterUids().size
        val result = runBlocking { repository.addToFavorites("no such id") }
        val after = dao.loadCharacterUids().size

        assertFalse(result)
        assertEquals(before, after)
    }

    @Test
    fun removeFromFavorites_works() = runBlocking {
        val before = dao.loadCharacterUids().size
        repository.removeFromFavorites("CHMA00001")
        val after = dao.loadCharacterUids().size

        assertEquals(before - 1, after)
    }

    @Test
    fun loadDetails_works() = runBlocking {
        val janeway = repository.loadDetails("CHMA00001")

        assertNotNull(janeway)
        assertEquals(janeway?.uid, "CHMA00001")
        assertEquals(janeway?.name, "Kathryn Janeway")
        assertEquals(janeway?.gender, "F")
        assertEquals(janeway?.yearOfBirth, null)
        assertEquals(janeway?.yearOfDeath, null)
        assertEquals(janeway?.weight, null)
        assertEquals(janeway?.height, null)
    }

    @Test
    fun loadDetails_wrongId_works() = runBlocking {
        val nothing = repository.loadDetails("no such character id exists")

        assertNull(nothing)
    }
}
