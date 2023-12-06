package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.data.mapper.fromDtoToProduct
import com.dexciuq.yummy_express.data.mapper.toAccessToken
import com.dexciuq.yummy_express.data.mapper.toAuthTokens
import com.dexciuq.yummy_express.data.mapper.toCategory
import com.dexciuq.yummy_express.data.mapper.toUser
import com.dexciuq.yummy_express.data.model.remote.LoginRequest
import com.dexciuq.yummy_express.data.model.remote.RegisterRequest
import com.dexciuq.yummy_express.domain.model.AccessToken
import com.dexciuq.yummy_express.domain.model.AuthTokens
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val yummyExpressApiService: YummyExpressBackendApiService
) : DataSource.Remote {
    override suspend fun getCategoryList(): List<Category> =
        yummyExpressApiService.getAllCategories().categories.toCategory()

    override suspend fun getHomeCategoryList(): List<Category> =
        yummyExpressApiService.getAllCategories().categories.toCategory().take(6)

    override suspend fun getProductsByCategory(category: Long): List<Product> =
        yummyExpressApiService.getAllProducts(category).products?.fromDtoToProduct() ?: listOf()

    override suspend fun getProductById(id: Long): Product =
        yummyExpressApiService.getProductById(id).product.fromDtoToProduct()

    override suspend fun getFeaturedProductList(): List<Product> =
        yummyExpressApiService.getAllProducts().products?.fromDtoToProduct()?.take(10) ?: listOf()

    override suspend fun login(email: String, password: String): AuthTokens =
        yummyExpressApiService.login(LoginRequest(email, password)).toAuthTokens()

    override suspend fun refresh(refreshToken: String): AccessToken =
        yummyExpressApiService.refresh(refreshToken).toAccessToken()

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String
    ) = yummyExpressApiService.register(
        RegisterRequest(name, surname, email, phoneNumber, password)
    )

    override suspend fun logout(accessToken: String) =
        yummyExpressApiService.logout(accessToken)

    override suspend fun getProfileInfo(accessToken: String) =
        yummyExpressApiService.getProfileInfo(accessToken).userDto.toUser()
}