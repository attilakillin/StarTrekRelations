package hu.attilakillin.startrekrelations.mocks

import hu.attilakillin.startrekrelations.persistence.Character
import hu.attilakillin.startrekrelations.persistence.CharacterDao
import hu.attilakillin.startrekrelations.persistence.CharacterWithRelations
import hu.attilakillin.startrekrelations.persistence.Relation

class MockCharacterDao : CharacterDao {
    private val entities = mutableListOf(
        Character(
            uid = "CHMA00001", name = "Kathryn Janeway", gender = "F", yearOfBirth = null,
            yearOfDeath = null, weight = null, height = null, species = "Human"
        ),
        Character(
            uid = "CHMA00002", name = "Spock", gender = "M", yearOfBirth = null,
            yearOfDeath = null, weight = null, height = null, species = "Vulcan"
        )
    )

    override fun searchCharacters(name: String): List<Character> {
        return entities.filter { it.name.lowercase().contains(name.lowercase()) }
    }

    override fun characterExists(uid: String): Boolean {
        return entities.any { it.uid == uid }
    }

    override fun loadCharacterUids(): List<String> {
        return entities.map { it.uid }
    }

    override fun insertCharacterAndRelations(character: Character, relations: List<Relation>) {
        entities.add(character)
    }

    override fun removeCharacter(uid: String) {
        entities.removeIf { it.uid == uid }
    }

    override fun loadCharacterDetails(uid: String): CharacterWithRelations {
        return CharacterWithRelations(
            character = entities.first { it.uid == uid },
            relations = listOf()
        )
    }
}
