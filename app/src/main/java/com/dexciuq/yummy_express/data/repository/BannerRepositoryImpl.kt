package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.repository.BannerRepository
import com.dexciuq.yummy_express.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val localDataSource: DataSource.Local,
) : BannerRepository {

    override suspend fun getBanners(): Flow<Resource<List<Banner>>> = flow {
        emit(Resource.Loading)
        try {
            val response = localDataSource.getBanners()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}