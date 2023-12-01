package com.dexciuq.yummy_express.data.data_source.preference

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.dexciuq.yummy_express.common.Constants
import com.dexciuq.yummy_express.data.data_source.DataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

class PreferenceDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) : DataSource.Preference {

//    init {
//        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
//            if (key == Constants.Preferences.DARK_MODE) {
//
//            }
//        }
//    }

    override suspend fun setOnBoardingComplete(isCompleted: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.ON_BOARDING_COMPLETE, isCompleted)
            .apply()
    }

    override suspend fun getOnBoardingComplete(): Boolean =
        sharedPreferences.getBoolean(
            Constants.Preferences.ON_BOARDING_COMPLETE,
            false
        )

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.DARK_MODE, isDarkMode).apply()
    }

    override suspend fun getDarkMode(): Boolean {
        var isDarkMode = false

        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                isDarkMode = true
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                isDarkMode = false
            }
        }

        return sharedPreferences.getBoolean(
            Constants.Preferences.DARK_MODE,
            isDarkMode
        )
    }

    override suspend fun setCurrentLanguage(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_NAME, language).apply()
    }

    override suspend fun getCurrentLanguage(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_NAME,
            Locale.getDefault().displayLanguage
        ) ?: Locale.getDefault().displayLanguage

    override suspend fun setCurrentLanguageCode(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_CODE, language).apply()
    }

    override suspend fun getCurrentLanguageCode(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_CODE,
            Locale.getDefault().language
        ) ?: Locale.getDefault().language
}