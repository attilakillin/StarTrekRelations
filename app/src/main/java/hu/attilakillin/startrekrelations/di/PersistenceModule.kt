package hu.attilakillin.startrekrelations.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import hu.attilakillin.startrekrelations.persistence.CharacterDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideCharacterDatabase(application: Application): CharacterDatabase {
        return Room
            .databaseBuilder(application, CharacterDatabase::class.java, "database")
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao {
        return database.characterDao()
    }
}
