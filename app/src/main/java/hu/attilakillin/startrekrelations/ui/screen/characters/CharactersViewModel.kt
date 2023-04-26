package hu.attilakillin.startrekrelations.ui.screen.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.attilakillin.startrekrelations.network.CharacterBase
import hu.attilakillin.startrekrelations.repository.CharacterRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    /* Main state exposed to related views, with a mutable backing field. */
    private var _characters = MutableLiveData<List<CharacterBase>>()
    val characters: LiveData<List<CharacterBase>>
        get() = _characters

    /* We save search metadata to allow searching for more results. */
    private var lastPage: Boolean = true
    private var pageNumber: Int = 0
    private var lastQuery: String = ""

    /* Searches characters with the given name query. */
    fun searchCharacters(query: String = "") = viewModelScope.launch {
        lastQuery = query
        pageNumber = 0

        val response = repository.searchCharacters(query, pageNumber = 0)

        lastPage = response.page.lastPage
        _characters.value = response.characters
    }

    /* Extends the character list with the next page of results. */
    fun searchMoreCharacters() = viewModelScope.launch {
        if (lastPage) return@launch

        pageNumber += 1

        val response = repository.searchCharacters(lastQuery, pageNumber)
        lastPage = response.page.lastPage
        _characters.value = (_characters.value ?: listOf()) + response.characters
    }
}
