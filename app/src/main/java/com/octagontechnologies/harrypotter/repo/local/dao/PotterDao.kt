package com.octagontechnologies.harrypotter.repo.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.octagontechnologies.harrypotter.repo.local.entity.LocalCharacterEntity

@Dao
abstract class PotterDao: BaseDao<LocalCharacterEntity>() {

    @Query("SELECT * FROM local_potter WHERE isStudent = :isStudent")
    abstract suspend fun getCharactersInCategory(isStudent: Boolean): List<LocalCharacterEntity>?

    @Query("SELECT * FROM local_potter")
    abstract suspend fun getCharacters(): List<LocalCharacterEntity>?

    @Query("SELECT * FROM local_potter WHERE ID = :characterID")
    abstract suspend fun getSpecificCharacter(characterID: String): LocalCharacterEntity?

}