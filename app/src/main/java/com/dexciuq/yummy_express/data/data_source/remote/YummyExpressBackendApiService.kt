package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.model.remote.category.CategoriesResponse
import com.dexciuq.yummy_express.data.model.remote.auth.LoginRequest
import com.dexciuq.yummy_express.data.model.remote.auth.LoginResponse
import com.dexciuq.yummy_express.data.model.remote.order.OrderRequest
import com.dexciuq.yummy_express.data.model.remote.order.OrderResponse
import com.dexciuq.yummy_express.data.model.remote.order.OrdersResponse
import com.dexciuq.yummy_express.data.model.remote.product.ProductResponse
import com.dexciuq.yummy_express.data.model.remote.product.ProductsResponse
import com.dexciuq.yummy_express.data.model.remote.auth.RefreshResponse
import com.dexciuq.yummy_express.data.model.remote.auth.RegisterRequest
import com.dexciuq.yummy_express.data.model.remote.auth.UserResponse
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface YummyExpressBackendApiService {

    @GET("/v1/categories")
    suspend fun getAllCategories(): CategoriesResponse

    @GET("/v1/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): ProductResponse

    @GET("/v1/products")
    suspend fun getAllProducts(): ProductsResponse

//    @FormUrlEncoded
    @GET("/v1/products")
    suspend fun getAllProducts(
        @Query("name") name: String?,
        @Query("category") category: Long?,
        @Query("brand") brand: String?,
        @Query("page") page: Int?,
        @Query("page_size") pageSize: Int?,
        @Query("sort") sort: String?
    ): ProductsResponse

    @POST("/v1/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    )

    @POST("/v1/auth/authenticate")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse?

    @GET("/v1/auth/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String,
    )

    @GET("/v1/profile/me")
    suspend fun getProfileInfo(
        @Header("Authorization") accessToken: String,
    ): UserResponse

    @GET("/v1/auth/refresh")
    suspend fun refresh(
        @Header("Authorization") refreshToken: String,
    ): RefreshResponse

    @POST("/v1/orders")
    suspend fun makeOrder(
        @Header("Authorization") accessToken: String,
        @Body orderRequest: OrderRequest,
    )

    @GET("/v1/orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: Long
    ): OrderResponse

    @GET("/v1/profile/orders")
    suspend fun getOrders(
        @Header("Authorization") accessToken: String,
    ): OrdersResponse
}