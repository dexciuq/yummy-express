package com.dexciuq.yummy_express.data.data_source.remote.token

import android.content.Context
import com.dexciuq.yummy_express.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        Constants.Preferences.SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun getAccessToken(): String? {
        return sharedPreferences.getString(
            Constants.Preferences.ACCESS_TOKEN, null
        )
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(
            Constants.Preferences.REFRESH_TOKEN, null
        )
    }

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit()
            .putString(Constants.Preferences.ACCESS_TOKEN, accessToken)
            .apply()
    }

    fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit()
            .putString(Constants.Preferences.REFRESH_TOKEN, refreshToken)
            .apply()
    }
}