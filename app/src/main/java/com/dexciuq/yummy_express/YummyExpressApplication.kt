package com.dexciuq.yummy_express

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YummyExpressApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(getString(R.string.yandex_map_api_key))
    }
}