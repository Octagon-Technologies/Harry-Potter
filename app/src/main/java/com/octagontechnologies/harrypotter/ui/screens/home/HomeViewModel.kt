package com.octagontechnologies.harrypotter.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.CharacterGroup
import com.octagontechnologies.harrypotter.repo.PotterRepo
import com.octagontechnologies.harrypotter.utils.NetworkMoniter
import com.octagontechnologies.harrypotter.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val potterRepo: PotterRepo,
    private val networkMoniter: NetworkMoniter
) : ViewModel() {

    private val _characters: MutableStateFlow<Resource<List<Character>>> =
        MutableStateFlow(Resource.Loading())
    val characters: StateFlow<Resource<List<Character>>> = _characters


    init {
        fetchCharacters(CharacterGroup.list.first())
    }


    fun fetchCharacters(characterGroup: CharacterGroup) {
        viewModelScope.launch {
            _characters.value = Resource.Loading()

            val charactersResult = potterRepo.fetchCharacters(characterGroup)
            _characters.value = charactersResult

            Timber.d("charactersResult.data is ${charactersResult.data}")

            // IF there is a network error, set up a network listener that retries the call once the network is up again
            if (charactersResult is Resource.Error) {
                networkMoniter.registerNetworkListener { isNetworkAvailable ->
                    Timber.d("isNetworkAvailable is $isNetworkAvailable")

                    if (isNetworkAvailable) {
                        _characters.value = Resource.Loading()

                        viewModelScope.launch {
                            _characters.value = potterRepo.fetchCharacters(characterGroup)
                        }
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        networkMoniter.unregisterNetworkListener()
    }

}