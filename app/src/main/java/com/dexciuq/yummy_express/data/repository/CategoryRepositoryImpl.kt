package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remote: DataSource.Remote
) : CategoryRepository {
    override suspend fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getCategoryList()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getHomeCategoryList(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getHomeCategoryList()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}