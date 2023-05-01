package hu.attilakillin.startrekrelations.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character WHERE name LIKE '%' || :name || '%'")
    fun searchCharacters(name: String): List<Character>

    @Insert
    fun insertCharacterAndRelations(character: Character, relations: List<Relation>)

    @Delete
    fun removeCharacter(character: Character)

    @Transaction
    @Query("SELECT * FROM character WHERE character.uid = :uid")
    fun loadCharacterDetails(uid: String): CharacterWithRelations
}
