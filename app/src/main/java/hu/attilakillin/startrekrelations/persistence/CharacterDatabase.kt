package hu.attilakillin.startrekrelations.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.Relation

@Database(entities = [Character::class, Relation::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
