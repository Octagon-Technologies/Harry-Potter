package com.octagontechnologies.harrypotter.di

import com.octagontechnologies.harrypotter.repo.PotterRepo
import com.octagontechnologies.harrypotter.repo.PotterRepoImpl
import com.octagontechnologies.harrypotter.repo.network.PotterApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

//const val POTTER_BASE_URL = "https://hp-api.onrender.com/api/characters/"
const val POTTER_BASE_URL = "https://hp-api.onrender.com/"


val networkModule = module {
    single<Moshi> {
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    single<PotterApi> {
        Retrofit.Builder()
            .baseUrl(POTTER_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(get())
            )
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            )
            .build()
            .create<PotterApi>()
    }

    single<PotterRepo> { PotterRepoImpl(get(), get()) }
}