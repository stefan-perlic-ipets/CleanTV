package com.perla.cleantv.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CleanTvApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CleanTvApplication)
            modules(appModule)
        }
    }
}