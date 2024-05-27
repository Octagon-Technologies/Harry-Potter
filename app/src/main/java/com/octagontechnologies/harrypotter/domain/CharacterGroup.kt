package com.octagontechnologies.harrypotter.domain


sealed class CharacterGroup (val groupName: String, val path: String) {
    data object Students: CharacterGroup("Students", "students")
    data object Staff: CharacterGroup("Staff", "staff")
    data object All: CharacterGroup("All", "")

    companion object {
        val list = listOf(Students, Staff, All)
    }
}
