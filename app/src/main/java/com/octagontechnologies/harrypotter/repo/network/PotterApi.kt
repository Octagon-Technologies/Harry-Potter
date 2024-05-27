package com.octagontechnologies.harrypotter.repo.network

import com.octagontechnologies.harrypotter.repo.network.models.PotterResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Network API containing Harry Potter characters
 * @link https://hp-api.onrender.com/
 *
 * BASE URL ~ https://hp-api.onrender.com/api/characters
 */
interface PotterApi {


    /**
     * Fetches the characters in Harry Potter
     *
     * @param type - Type can be: students OR staff
     */
    @GET("/api/characters/{type}")
    suspend fun getCharacters(
        @Path("type") type: String
    ): List<PotterResponseItem>

    /**
     * Gets the details of a specific character
     */
    @GET("/api/character/{character_id}")
    suspend fun getCharacterDetails(
        @Path("character_id", encoded = true) characterID: String
    ): List<PotterResponseItem>

    /**
     * Fetches all characters in Harry Potter
     */
    @GET("/api/characters")
    suspend fun getAllCharacters(): List<PotterResponseItem>

}