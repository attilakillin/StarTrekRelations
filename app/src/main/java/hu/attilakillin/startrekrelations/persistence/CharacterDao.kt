package hu.attilakillin.startrekrelations.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.CharacterWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun loadAllCharacters(): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM character WHERE character.uid = :uid")
    fun loadCharacterDetails(uid: String): Flow<CharacterWithRelations>
}
