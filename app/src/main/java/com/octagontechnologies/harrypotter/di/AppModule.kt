package com.octagontechnologies.harrypotter.di

import com.octagontechnologies.harrypotter.ui.screens.details.DetailsViewModel
import com.octagontechnologies.harrypotter.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
}