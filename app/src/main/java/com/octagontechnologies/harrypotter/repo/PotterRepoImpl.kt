package com.octagontechnologies.harrypotter.repo

import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.CharacterGroup
import com.octagontechnologies.harrypotter.repo.local.dao.PotterDao
import com.octagontechnologies.harrypotter.repo.local.dto.toCharacter
import com.octagontechnologies.harrypotter.repo.local.dto.toCharacters
import com.octagontechnologies.harrypotter.repo.local.dto.toLocalCharacters
import com.octagontechnologies.harrypotter.repo.network.PotterApi
import com.octagontechnologies.harrypotter.repo.network.dto.toCharacters
import com.octagontechnologies.harrypotter.utils.ErrorType
import com.octagontechnologies.harrypotter.utils.Resource
import timber.log.Timber

class PotterRepoImpl(private val potterApi: PotterApi, private val potterDao: PotterDao) :
    PotterRepo {

    override suspend fun fetchCharacters(characterGroup: CharacterGroup): Resource<List<Character>> =
        try {
            fetchCharactersRemote(characterGroup)
        } catch (exception: Exception) {
            fetchCharactersLocal(characterGroup)
        }


    private suspend fun fetchCharactersRemote(characterGroup: CharacterGroup): Resource.Success<List<Character>> {
        val characters = if (characterGroup is CharacterGroup.All)
            potterApi.getAllCharacters().toCharacters()
        else
            potterApi.getCharacters(type = characterGroup.path).toCharacters()

        potterDao.insertData(characters.toLocalCharacters())

        Timber.d("Potter characters: $characters")
        return Resource.Success(characters)
    }


    private suspend fun fetchCharactersLocal(characterGroup: CharacterGroup): Resource<List<Character>> {
        val localCharacters = if (characterGroup is CharacterGroup.All)
            potterDao.getCharacters()?.toCharacters()
        else
            potterDao.getCharactersInCategory(characterGroup is CharacterGroup.Students)
                ?.toCharacters()

        return if (localCharacters.isNullOrEmpty())
            Resource.Error(ErrorType.NoNetworkError)
        else
            Resource.Success(localCharacters)
    }


    override suspend fun getCharacterDetails(characterId: String): Resource<Character> {
        val character = potterDao.getSpecificCharacter(characterId)?.toCharacter()
        Timber.d("Character is $character")

        return if (character != null)
            Resource.Success(character)
        else
            Resource.Error(ErrorType.NoNetworkError)
    }
}