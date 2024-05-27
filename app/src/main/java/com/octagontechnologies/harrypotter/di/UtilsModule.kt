package com.octagontechnologies.harrypotter.di

import com.octagontechnologies.harrypotter.utils.NetworkMoniter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val utilsModule = module {
    single<NetworkMoniter> {
        NetworkMoniter(androidContext())
    }
}