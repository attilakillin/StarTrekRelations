package hu.attilakillin.startrekrelations.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.Relation

@Database(entities = [Character::class, Relation::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        private var instance: CharacterDatabase? = null

        fun get(context: Context): CharacterDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, CharacterDatabase::class.java, "database")
                    .build()
                    .also { instance = it }
            }
        }
    }
}
