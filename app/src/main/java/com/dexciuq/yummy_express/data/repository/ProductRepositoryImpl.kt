package com.dexciuq.yummy_express.data.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Filter
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
            val remoteProducts = remote.getFeaturedProductList()
            val cartProductsFlow = local.getAllProductsFromCart()

            cartProductsFlow.collect { cartProducts ->
                val mergedProducts = remoteProducts.map { remoteProduct ->
                    val cartProduct = cartProducts.find { it.id == remoteProduct.id }
                    remoteProduct.amount = cartProduct?.amount
                    remoteProduct
                }
                emit(Resource.Success(mergedProducts))
            }
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override suspend fun getProductsByFilter(filter: Filter): Flow<Resource<List<Product>>> =
        flow {
            emit(Resource.Loading)
            try {
                val remoteProducts = if (filter.category != null && filter.category.id == 1L) {
                    remote.getProductsByFilterWithDiscount(filter)
                } else {
                    remote.getProductsByFilter(filter)
                }
                val cartProductsFlow = local.getAllProductsFromCart()

                cartProductsFlow.collect { cartProducts ->
                    val mergedProducts = remoteProducts.map { remoteProduct ->
                        val cartProduct = cartProducts.find { it.id == remoteProduct.id }
                        remoteProduct.amount = cartProduct?.amount
                        remoteProduct
                    }

                    var final = mergedProducts
                    final = when (filter.sort) {
                        "price" -> final.sortedBy { it.calculatePrice }
                        "-price" -> final.sortedByDescending { it.calculatePrice }
                        else -> final.sortedBy { it.id }
                    }
                    emit(Resource.Success(final))
                }
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

    override suspend fun getProductByUPC(upc: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading)
        try {
            val response = remote.getProductByUPC(upc)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getCartItemCount(): Flow<Resource<Int>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getCartItemCount()
            response.collect {
                emit(Resource.Success(it))
            }
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }

    override fun getAllProductsFromCart(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val response = local.getAllProductsFromCart()
            response.collect {
                emit(Resource.Success(it))
            }
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