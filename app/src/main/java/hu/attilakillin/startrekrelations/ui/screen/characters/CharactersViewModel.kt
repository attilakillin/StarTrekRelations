package hu.attilakillin.startrekrelations.ui.screen.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.network.CharacterBase
import hu.attilakillin.startrekrelations.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    var characters: Flow<List<CharacterBase>> = repository.loadCharacters("")
        private set

    fun searchCharacters(name: String = "") = viewModelScope.launch {
        characters = repository.loadCharacters(name)
    }
}
