package com.octagontechnologies.harrypotter.repo

import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.CharacterGroup
import com.octagontechnologies.harrypotter.utils.Resource

interface PotterRepo {

    /**
     * Fetches Characters
     */
    suspend fun fetchCharacters(characterGroup: CharacterGroup): Resource<List<Character>>

    /**
     * Gets the details of a specific character
     */
    suspend fun getCharacterDetails(characterId: String): Resource<Character>

}