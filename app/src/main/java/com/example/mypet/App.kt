package com.example.mypet

import android.app.Application
import com.example.mypet.app.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // map activation
        MapKitFactory.setApiKey(BuildConfig.yandexMapApiKey)
    }
}