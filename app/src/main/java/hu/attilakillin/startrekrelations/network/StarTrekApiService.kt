package hu.attilakillin.startrekrelations.network

interface StarTrekApiService {
    suspend fun searchCharacters(query: String)

    suspend fun getCharacterDetails(uid: String)

    companion object {
        const val API_URL = "https://stapi.co/api/v1/rest"
    }
}
