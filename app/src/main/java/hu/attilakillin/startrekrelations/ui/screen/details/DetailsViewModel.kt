package hu.attilakillin.startrekrelations.ui.screen.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.attilakillin.startrekrelations.persistence.CharacterWithRelations
import hu.attilakillin.startrekrelations.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    val details: Flow<CharacterWithRelations> = repository.loadDetails(TODO())
}
