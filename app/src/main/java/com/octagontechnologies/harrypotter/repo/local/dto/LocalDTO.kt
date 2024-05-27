package com.octagontechnologies.harrypotter.repo.local.dto

import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.repo.local.entity.LocalCharacterEntity

fun List<Character>.toLocalCharacters() =
    map { it.toLocalCharacter() }

fun Character.toLocalCharacter() = LocalCharacterEntity(
    ID = ID,
    isStudent = isStudent,
    actorName = actorName,
    realName = realName,
    image = image,
    ancestry = ancestry,
    guardian = guardian,
    species = species,
    gender = gender,
    eyeColor = eyeColor,
    hairColor = hairColor,
    wand = wand,
    house = house
)

fun List<LocalCharacterEntity>.toCharacters() = map { it.toCharacter() }

fun LocalCharacterEntity.toCharacter() = Character(
    ID,
    isStudent,
    actorName,
    realName,
    image,
    ancestry,
    guardian,
    species,
    gender,
    eyeColor,
    hairColor,
    wand,
    house
)