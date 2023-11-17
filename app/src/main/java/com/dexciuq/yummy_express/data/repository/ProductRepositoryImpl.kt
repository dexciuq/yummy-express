package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import com.dexciuq.yummy_express.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    // todo
) : ProductRepository {

    override suspend fun getFeaturedProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val response = listOf<Product>()  // todo
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}