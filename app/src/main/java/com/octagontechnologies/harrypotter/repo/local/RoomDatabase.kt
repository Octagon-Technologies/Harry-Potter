package com.octagontechnologies.harrypotter.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.octagontechnologies.harrypotter.repo.local.dao.PotterDao
import com.octagontechnologies.harrypotter.repo.local.entity.LocalCharacterEntity

@Database(entities = [LocalCharacterEntity::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val potterDao: PotterDao

}