package com.soyvictorherrera.nosedive

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        installTimber()
    }

    private fun installTimber() {
        if(!BuildConfig.DEBUG) return
        Timber.plant(Timber.DebugTree())
    }
}
