package com.octagontechnologies.harrypotter.repo.local

import androidx.room.TypeConverter
import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.Wand
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RoomTypeConverters {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val characterAdapter = moshi.adapter(Character::class.java)
    private val wandAdapter = moshi.adapter(Wand::class.java)

    @TypeConverter
    fun jsonToCharacter(json: String) = characterAdapter.fromJson(json)
    @TypeConverter
    fun characterToJson(character: Character) = characterAdapter.toJson(character)


    @TypeConverter
    fun jsonToWand(json: String) = wandAdapter.fromJson(json)
    @TypeConverter
    fun wandToJson(wand: Wand) = wandAdapter.toJson(wand)
}