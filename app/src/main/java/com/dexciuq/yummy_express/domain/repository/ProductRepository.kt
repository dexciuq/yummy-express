package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getFeaturedProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductsByCategory(category: Long): Flow<Resource<List<Product>>>
    suspend fun getProductById(id: Long): Flow<Resource<Product>>
    fun getCartItemCount(): Flow<Resource<Int>>
    fun getAllProductsFromCart(): Flow<Resource<List<Product>>>
    suspend fun getCartProductById(id: Long): Flow<Resource<Product>>
    suspend fun addProductToCart(product: Product)
    suspend fun removeProductFromCart(product: Product)
    suspend fun removeAllProductFromCart()
}