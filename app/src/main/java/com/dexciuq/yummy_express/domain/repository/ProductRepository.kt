package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getFeaturedProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductsByCategory(category: Long): Flow<Resource<List<Product>>>
}