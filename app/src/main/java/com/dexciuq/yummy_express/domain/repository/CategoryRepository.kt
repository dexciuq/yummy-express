package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Resource<List<Category>>>
    suspend fun getHomeCategoryList(): Flow<Resource<List<Category>>>
}