package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.repository.UIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UIRepositoryImpl @Inject constructor(
    private val local: DataSource.Local,
    private val preference: DataSource.Preference,
) : UIRepository {

    override suspend fun getBanners(): Flow<Resource<List<Banner>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getBanners()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getOnBoardingItems(): Flow<Resource<List<OnBoarding>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getOnBoardingItems()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getOnBoardingComplete(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getOnBoardingComplete()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun setOnBoardingComplete(isCompleted: Boolean) {
        preference.setOnBoardingComplete(isCompleted)
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) =
        preference.setDarkMode(isDarkMode)

    override suspend fun getDarkMode(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getDarkMode()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getCurrentLanguage(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getCurrentLanguage()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun setCurrentLanguage(language: String) =
        preference.setCurrentLanguage(language)

    override suspend fun getCurrentLanguageCode(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val response = preference.getCurrentLanguageCode()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun setCurrentLanguageCode(languageCode: String) =
        preference.setCurrentLanguageCode(languageCode)

    override fun getLanguages(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading)
        try {
            val response = listOf("en", "ru")
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}