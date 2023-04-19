package hu.attilakillin.startrekrelations.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(StarTrekApiService.API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideStarTrekApiService(retrofit: Retrofit): StarTrekApiService {
        return retrofit.create(StarTrekApiService::class.java)
    }
}
