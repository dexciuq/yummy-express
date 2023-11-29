package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: DataSource.Remote,
    private val local: DataSource.Local
) : ProductRepository {

    override suspend fun getFeaturedProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getFeaturedProductList()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getProductsByCategory(category: Long): Flow<Resource<List<Product>>> =
        flow {
            emit(Resource.Loading)
            try {
                val response = remote.getProductsByCategory(category)
                emit(Resource.Success(response))
            } catch (t: Throwable) {
                emit(Resource.Error(t))
            }
        }

    override suspend fun getProductById(id: Long): Flow<Resource<Product>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getProductById(id)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getAllProductsFromCart(): Flow<Resource<Flow<List<Product>>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getAllProductsFromCart()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getCartProductById(id: Long): Flow<Resource<Product>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getCartProductById(id) ?: error("Product not found")
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun addProductToCart(product: Product) =
        local.addProductToCart(product)

    override suspend fun removeProductFromCart(product: Product) =
        local.removeProductFromCart(product)

    override suspend fun removeAllProductFromCart() =
        local.removeAllProductFromCart()
}