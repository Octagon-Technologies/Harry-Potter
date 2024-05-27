package com.octagontechnologies.harrypotter

import android.app.Application
import com.octagontechnologies.harrypotter.di.appModule
import com.octagontechnologies.harrypotter.di.localModule
import com.octagontechnologies.harrypotter.di.networkModule
import com.octagontechnologies.harrypotter.di.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule, localModule, networkModule, utilsModule)
        }
    }

}