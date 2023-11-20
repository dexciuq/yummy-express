package com.dexciuq.yummy_express.data.data_source.preference

import android.content.SharedPreferences
import com.dexciuq.yummy_express.common.Constants
import com.dexciuq.yummy_express.data.data_source.DataSource
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DataSource.Preference {

    override suspend fun setOnBoardingComplete(isCompleted: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.ON_BOARDING_COMPLETE, isCompleted)
            .apply()
    }

    override suspend fun getOnBoardingComplete(): Boolean =
        sharedPreferences.getBoolean(
            Constants.Preferences.ON_BOARDING_COMPLETE,
            false
        )
}