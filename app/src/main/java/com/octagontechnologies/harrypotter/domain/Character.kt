package com.octagontechnologies.harrypotter.domain

data class Character(
    val ID: String,
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
) {

    fun toCharacterMap() = mapOf(
        "Ancestry" to ancestry,
        "Patronus/Guardian" to guardian,
        "Species" to species,
        "Gender" to gender,
        "Eye Color" to eyeColor,
        "Hair Color" to hairColor,
        "Wand" to wand.asStatement(),
        "House" to house
    )

    companion object {
        val TEST_CHARACTER =
            Character(
                "abcde",
                true,
                "Andrew",
                "Da Chelimo",
                null,
                "The Chelimos",
                "Master Chelimo",
                "Chelimo Sapiens",
                "Sigmas",
                "Blue",
                "Blonde",
                // "wood":"holly","core":"phoenix tail feather","length":11
                Wand("Phoenix Tail Feather", 11.0, "Holly"),
                "Gryfindor"
            )

        val LIST_OF_CHARACTERS =
            listOf(TEST_CHARACTER, TEST_CHARACTER, TEST_CHARACTER, TEST_CHARACTER, TEST_CHARACTER)
    }

}