package com.example.mypet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //MapKitFactory.setApiKey(BuildConfig.yandexMapApiKey)
    }
}