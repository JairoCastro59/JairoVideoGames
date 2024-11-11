package com.jdcastro.jairovideogames.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VideogameApplication : Application() {

    override fun onCreate() {
        super.onCreate() // Injection happens in super.onCreate()
    }
}