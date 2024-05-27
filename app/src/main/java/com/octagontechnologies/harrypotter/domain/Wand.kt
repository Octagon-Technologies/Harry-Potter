package com.octagontechnologies.harrypotter.domain

data class Wand(
    val core: String,
    val length: Double,
    val wood: String
) {

    fun asStatement() = "Core: $core  Wood: $wood  Length: $length"

}