package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.OnBoarding
import kotlinx.coroutines.flow.Flow

interface UIRepository {
    suspend fun getBanners(): Flow<Resource<List<Banner>>>
    suspend fun getOnBoardingItems(): Flow<Resource<List<OnBoarding>>>
    suspend fun getOnBoardingComplete(): Flow<Resource<Boolean>>
    suspend fun setOnBoardingComplete(isCompleted: Boolean)
    suspend fun setDarkMode(isDarkMode: Boolean)
    suspend fun getDarkMode(): Flow<Resource<Boolean>>
    suspend fun getCurrentLanguage(): Flow<Resource<String>>
    suspend fun setCurrentLanguage(language: String)
    suspend fun getCurrentLanguageCode(): Flow<Resource<String>>
    suspend fun setCurrentLanguageCode(languageCode: String)
    fun getLanguages(): Flow<Resource<List<String>>>
}