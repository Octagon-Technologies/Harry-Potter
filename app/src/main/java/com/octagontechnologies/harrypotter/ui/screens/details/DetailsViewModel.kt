package com.octagontechnologies.harrypotter.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.repo.PotterRepo
import com.octagontechnologies.harrypotter.ui.navigation.Screens
import com.octagontechnologies.harrypotter.utils.NetworkMoniter
import com.octagontechnologies.harrypotter.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val potterRepo: PotterRepo,
    private val networkMoniter: NetworkMoniter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _character: MutableStateFlow<Resource<Character>> =
        MutableStateFlow(Resource.Loading())
    val character: StateFlow<Resource<Character>> = _character

    private val characterID =
        requireNotNull(savedStateHandle.get<String>(Screens.DETAILS_CHARACTER_ID))


    init {
        viewModelScope.launch {
            val characterResult = potterRepo.getCharacterDetails(characterID)
            _character.value = characterResult
        }
    }


    override fun onCleared() {
        super.onCleared()
        networkMoniter.unregisterNetworkListener()
    }

}