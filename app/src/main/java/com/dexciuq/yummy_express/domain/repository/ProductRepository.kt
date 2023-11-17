package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getFeaturedProducts(): Flow<Resource<List<Product>>>
}