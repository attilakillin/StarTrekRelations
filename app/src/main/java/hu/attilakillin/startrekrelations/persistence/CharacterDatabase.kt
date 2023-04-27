package hu.attilakillin.startrekrelations.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Character::class, Relation::class], version = 2, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
