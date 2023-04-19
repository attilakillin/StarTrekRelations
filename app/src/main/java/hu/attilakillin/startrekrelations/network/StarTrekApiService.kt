package hu.attilakillin.startrekrelations.network

private const val API_URL = "https://stapi.co/api/v1/rest"

object StarTrekApi {
    val service: StarTrekApiService = TODO()
}

interface StarTrekApiService {
    suspend fun searchCharacters(query: String)
    suspend fun getCharacterDetails(uid: String)
}
