package com.octagontechnologies.harrypotter.repo.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.octagontechnologies.harrypotter.domain.Wand

@Entity(tableName = "local_potter")
data class LocalCharacterEntity (
    @PrimaryKey
    val ID: String,

    /**
     * Important for filtering out students, staff or all
     */
    val isStudent: Boolean,

    val actorName: String,
    val realName: String,
    val image: String?,
    val ancestry: String,
    val guardian: String, // Same as patronus
    val species: String,
    val gender: String,
    val eyeColor: String,
    val hairColor: String,
    val wand: Wand,
    val house: String
)