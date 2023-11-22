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

    override fun getAllProductsFromCart(): Flow<List<Product>> =
        local.getAllProductsFromCart()

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

    override suspend fun removeProductFromCart(id: Long) =
        local.removeProductFromCart(id)

    override suspend fun removeAllProductFromCart() =
        local.removeAllProductFromCart()

    override suspend fun updateProductAmount(id: Long, amount: Int) =
        local.removeProductFromCart(id)
}