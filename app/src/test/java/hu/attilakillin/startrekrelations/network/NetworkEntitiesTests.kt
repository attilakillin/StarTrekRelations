package hu.attilakillin.startrekrelations.network

import hu.attilakillin.startrekrelations.mocks.MockStarTrekApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkEntitiesTests {
    private val source = MockStarTrekApiService()

    @Test
    fun baseResponse_toModel_paging_works() = runBlocking {
        val response = source.searchCharacters("")
        val converted = response.toModel()

        assertEquals(response.page.firstPage, converted.firstPage)
        assertEquals(response.page.lastPage, converted.lastPage)
        assertEquals(response.page.pageNumber, converted.pageNumber)
        assertEquals(response.page.pageSize, converted.content.size)
    }

    @Test
    fun baseResponse_toModel_list_works() = runBlocking {
        val response = source.searchCharacters("")
        val converted = response.toModel()

        assertEquals(response.characters.size, converted.content.size)
        assertEquals(response.characters.first().uid, converted.content.first().uid)
        assertEquals(response.characters.last().uid, converted.content.last().uid)
    }

    @Test
    fun baseResponse_toModel_empty_works() = runBlocking {
        val response = source.searchCharacters("surely no such name exists")
        val converted = response.toModel()

        assertEquals(converted.firstPage, true)
        assertEquals(converted.lastPage, true)
        assertEquals(converted.pageNumber, 0)
        assertEquals(converted.content.size, 0)
    }

    @Test
    fun baseResponse_toModel_filter_works() = runBlocking {
        val response = source.searchCharacters("Janeway")
        val converted = response.toModel()

        assertEquals(converted.content.size, 1)
        assertEquals(response.characters.first().name, "Kathryn Janeway")
        assertEquals(response.characters.last().name, "Kathryn Janeway")
    }

    @Test
    fun baseResponse_toModel_attributes_works() = runBlocking {
        val response = source.searchCharacters("Janeway")
        val janeway = response.toModel().content.first()

        assertEquals(janeway.uid, "CHMA00001")
        assertEquals(janeway.name, "Kathryn Janeway")
        assertEquals(janeway.gender, "F")
        assertEquals(janeway.yearOfBirth, null)
        assertEquals(janeway.yearOfDeath, null)
        assertEquals(janeway.weight, null)
        assertEquals(janeway.height, null)
    }

    @Test
    fun fullResponse_toModel_simple_attributes_work() = runBlocking {
        val response = source.getCharacterDetails("CHMA00001")
        val janeway = response.toModel()

        assertEquals(janeway.uid, "CHMA00001")
        assertEquals(janeway.name, "Kathryn Janeway")
        assertEquals(janeway.gender, "F")
        assertEquals(janeway.yearOfBirth, null)
        assertEquals(janeway.yearOfDeath, null)
        assertEquals(janeway.weight, null)
        assertEquals(janeway.height, null)
    }

    @Test
    fun fullResponse_toModel_complex_attributes_work() = runBlocking {
        val response = source.getCharacterDetails("CHMA00001")
        val janeway = response.toModel()

        assertEquals(janeway.species, "Human")
        assertEquals(janeway.relations.size, 1)
        assertEquals(janeway.relations.first().qualifier, "Good friend")
    }
}
