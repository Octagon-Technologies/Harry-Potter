package com.octagontechnologies.harrypotter.repo.network.dto

import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.Wand
import com.octagontechnologies.harrypotter.repo.network.models.PotterResponseItem
import com.octagontechnologies.harrypotter.repo.network.models.WandResponse

//fun PotterResponse.toCharacters() =
fun List<PotterResponseItem>.toCharacters() =
    map {
        with(it) {
            Character(
                ID = id,
                isStudent = hogwartsStudent,
                actorName = actor,
                realName = name,
                image = image,
                ancestry = ancestry,
                guardian = patronus,
                species = species,
                gender = gender,
                eyeColor = eyeColour,
                hairColor = hairColour,
                wand = wandResponse.toWand(),
                house = house
            )
        }
    }


fun WandResponse.toWand() =
    Wand(core, length ?: 0.0, wood)