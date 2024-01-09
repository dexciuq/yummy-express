package com.dexciuq.yummy_express.app

import android.app.Application
import com.dexciuq.yummy_express.R
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class YummyExpressApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        MapKitFactory.setApiKey(getString(R.string.yandex_map_api_key))
    }
}