package com.octagontechnologies.harrypotter.di

import android.content.Context
import androidx.room.Room
import com.octagontechnologies.harrypotter.repo.local.AppDatabase
import com.octagontechnologies.harrypotter.repo.local.dao.PotterDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single<AppDatabase> { providesAppDatabase(androidContext()) }
    single<PotterDao> { providePotterDao(get()) }

}

fun providesAppDatabase(context: Context) =
    Room.databaseBuilder(
        context = context.applicationContext,
        klass = AppDatabase::class.java,
        name = "AppDatabase"
    ).fallbackToDestructiveMigration().build()


fun providePotterDao(appDatabase: AppDatabase) = appDatabase.potterDao