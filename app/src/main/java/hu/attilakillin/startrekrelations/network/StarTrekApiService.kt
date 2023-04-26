package hu.attilakillin.startrekrelations.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StarTrekApiService {
    @FormUrlEncoded
    @POST("character/search")
    suspend fun searchCharacters(
        @Field("name") name: String,
        @Query("pageNumber") pageNumber: Int = 0,
        @Query("pageSize") pageSize: Int = 50
    ): CharacterBaseResponse

    @GET("character")
    suspend fun getCharacterDetails(
        @Query("uid") uid: String
    ): CharacterFullResponse

    companion object {
        const val API_URL = "https://stapi.co/api/v1/rest/"
    }
}
