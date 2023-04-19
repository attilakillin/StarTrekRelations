package hu.attilakillin.startrekrelations.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import hu.attilakillin.startrekrelations.network.StarTrekApiService
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import hu.attilakillin.startrekrelations.repository.CharacterRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        starTrekApiService: StarTrekApiService,
        characterDao: CharacterDao
    ): CharacterRepository {
        return CharacterRepository(starTrekApiService, characterDao)
    }
}
