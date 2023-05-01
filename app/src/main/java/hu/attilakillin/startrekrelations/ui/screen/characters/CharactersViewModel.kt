package hu.attilakillin.startrekrelations.ui.screen.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.PagedList
import hu.attilakillin.startrekrelations.repository.CharacterRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    /* Main state exposed to related views, with mutable backing fields. */
    private var _characters = MutableLiveData<PagedList<Character>>()
    val characters: LiveData<PagedList<Character>> get() = _characters

    private var _favorites = MutableLiveData<List<Character>>()
    val favorites: LiveData<List<Character>> get() = _favorites

    /* Search both the favorites and the general characters list. */
    fun searchAll(query: String = "") {
        searchFavorites(query)
        searchCharacters(query)
    }

    /* Searches favorites with the given nam query. */
    fun searchFavorites(query: String = "") = viewModelScope.launch {
        _favorites.value = repository.searchFavorites(query)
    }


    /* We save search metadata to allow searching for more results. */
    private var lastQuery: String = ""

    /* Searches characters with the given name query. */
    fun searchCharacters(query: String = "") = viewModelScope.launch {
        lastQuery = query
        _characters.value = repository.searchCharacters(query, pageNumber = 0)
    }

    /* Extends the character list with the next page of results. */
    fun searchMoreCharacters() = viewModelScope.launch {
        val current = _characters.value ?: return@launch
        if (current.lastPage) return@launch

        val response = repository.searchCharacters(lastQuery, current.pageNumber + 1)
        _characters.value = PagedList(
            content = current.content + response.content,
            pageNumber = response.pageNumber,
            firstPage = response.firstPage,
            lastPage = response.lastPage
        )
    }

    fun addToFavorites(uid: String) = viewModelScope.launch {
        val success = repository.addToFavorites(uid)
        if (success) {
            updateOneFavorite(uid, true)
        } else {
            this.cancel()
        }
    }

    fun removeFromFavorites(uid: String) = viewModelScope.launch {
        repository.removeFromFavorites(uid)
        updateOneFavorite(uid, false)
    }

    private fun updateOneFavorite(uid: String, favorite: Boolean) {
        searchFavorites(lastQuery)
        _characters.value = _characters.value?.also { list ->
            list.content.find { it.uid == uid }?.isFavorite = favorite
        }
    }
}
