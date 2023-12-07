package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.model.remote.CategoriesResponse
import com.dexciuq.yummy_express.data.model.remote.LoginRequest
import com.dexciuq.yummy_express.data.model.remote.LoginResponse
import com.dexciuq.yummy_express.data.model.remote.ProductResponse
import com.dexciuq.yummy_express.data.model.remote.ProductsResponse
import com.dexciuq.yummy_express.data.model.remote.RefreshResponse
import com.dexciuq.yummy_express.data.model.remote.RegisterRequest
import com.dexciuq.yummy_express.data.model.remote.UserResponse
import retrofit2.http.Body
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

    @GET("/v1/products")
    suspend fun getAllProducts(
        @Query("category") category: Long,
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
}