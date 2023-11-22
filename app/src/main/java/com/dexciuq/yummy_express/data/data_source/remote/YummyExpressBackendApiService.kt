package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.model.remote.CategoriesResponse
import com.dexciuq.yummy_express.data.model.remote.ProductResponse
import com.dexciuq.yummy_express.data.model.remote.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YummyExpressBackendApiService {

    @GET("/v1/categories")
    suspend fun getAllCategories(): CategoriesResponse

    @GET("/v1/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ) : ProductResponse

    @GET("/v1/products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("/v1/products")
    suspend fun getAllProducts(
        @Query("category") category: Long,
    ): ProductsResponse
}