package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.model.remote.auth.LoginRequest
import com.dexciuq.yummy_express.data.model.remote.auth.LoginResponse
import com.dexciuq.yummy_express.data.model.remote.auth.MessageResponse
import com.dexciuq.yummy_express.data.model.remote.auth.RefreshResponse
import com.dexciuq.yummy_express.data.model.remote.auth.RegisterRequest
import com.dexciuq.yummy_express.data.model.remote.auth.ResetCodeRequest
import com.dexciuq.yummy_express.data.model.remote.auth.ResetPasswordRequest
import com.dexciuq.yummy_express.data.model.remote.auth.UserResponse
import com.dexciuq.yummy_express.data.model.remote.auth.VerifyCodeRequest
import com.dexciuq.yummy_express.data.model.remote.category.CategoriesResponse
import com.dexciuq.yummy_express.data.model.remote.order.OrderRequest
import com.dexciuq.yummy_express.data.model.remote.order.OrderResponse
import com.dexciuq.yummy_express.data.model.remote.order.OrdersResponse
import com.dexciuq.yummy_express.data.model.remote.product.ProductResponse
import com.dexciuq.yummy_express.data.model.remote.product.ProductsResponse
import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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

    @GET("/v1/upc/{upc}")
    suspend fun getProductByUPC(
        @Path("upc") upc: String
    ): ProductResponse

    @GET("/v1/products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("/v1/products-wit-discount")
    suspend fun getAllProductsWithDiscount(
        @Query("name") name: String?,
        @Query("category") category: Long?,
        @Query("brand") brand: String?,
        @Query("page") page: Int?,
        @Query("page_size") pageSize: Int?,
        @Query("sort") sort: String?
    ): ProductsResponse

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
    ) : Response<UserResponse>

    @POST("/v1/auth/authenticate")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("/v1/auth/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String,
    )

    @PATCH("/v1/users/{id}")
    suspend fun updateUserById(
        @Path("id") id: Long,
        @Body registerRequest: RegisterRequest
    ): UserResponse

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

    @POST("/v1/request-password-reset")
    suspend fun sendResetCode(
        @Body resetCodeRequest: ResetCodeRequest
    ) : Response<MessageResponse>

    @POST("/v1/verify-reset-code")
    suspend fun verifyResetCode(
        @Body verifyCodeRequest: VerifyCodeRequest
    ) : Response<MessageResponse>

    @POST("/v1/reset-password")
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ) : Response<MessageResponse>
}