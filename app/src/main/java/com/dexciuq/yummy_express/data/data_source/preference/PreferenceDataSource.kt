package com.dexciuq.yummy_express.data.data_source.preference

import android.content.SharedPreferences
import com.dexciuq.yummy_express.common.Constants
import com.dexciuq.yummy_express.data.data_source.DataSource
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
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

    override suspend fun getAccessToken(): String =
        sharedPreferences.getString(
            Constants.Preferences.ACCESS_TOKEN,
            ""
        ) ?: ""

    override suspend fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(Constants.Preferences.ACCESS_TOKEN, accessToken)
            .apply()
    }

    override suspend fun getRefreshToken(): String =
        sharedPreferences.getString(
            Constants.Preferences.REFRESH_TOKEN,
            ""
        ) ?: ""

    override suspend fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(Constants.Preferences.REFRESH_TOKEN, refreshToken)
            .apply()
    }

    override suspend fun getAuthSkip(): Boolean =
        sharedPreferences.getBoolean(
            Constants.Preferences.AUTH_SKIP,
            false
        )

    override suspend fun setAuthSkip(skip: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.AUTH_SKIP, skip)
            .apply()
    }
}