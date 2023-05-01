package hu.attilakillin.startrekrelations.ui.screen.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.persistence.CharacterWithRelations
import hu.attilakillin.startrekrelations.repository.CharacterRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    /* Main state exposed to related views, with mutable backing fields. */
    private var _details = MutableLiveData<Character>()
    val details: LiveData<Character> get() = _details

    fun loadDetails(uid: String) = viewModelScope.launch {
        val character = repository.loadDetails(uid) ?: return@launch this.cancel()
        _details.value = character
    }
}
