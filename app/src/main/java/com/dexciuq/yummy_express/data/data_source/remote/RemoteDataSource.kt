package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.data.data_source.remote.token.SessionManager
import com.dexciuq.yummy_express.data.mapper.fromDtoToDomain
import com.dexciuq.yummy_express.data.mapper.fromDtoToProduct
import com.dexciuq.yummy_express.data.mapper.toAccessToken
import com.dexciuq.yummy_express.data.mapper.toAuthTokens
import com.dexciuq.yummy_express.data.mapper.toCategory
import com.dexciuq.yummy_express.data.mapper.toDomain
import com.dexciuq.yummy_express.data.mapper.toUser
import com.dexciuq.yummy_express.data.model.remote.auth.LoginRequest
import com.dexciuq.yummy_express.data.model.remote.auth.RegisterRequest
import com.dexciuq.yummy_express.data.model.remote.auth.ResetCodeRequest
import com.dexciuq.yummy_express.data.model.remote.auth.ResetPasswordRequest
import com.dexciuq.yummy_express.data.model.remote.auth.VerifyCodeRequest
import com.dexciuq.yummy_express.data.model.remote.order.OrderRequest
import com.dexciuq.yummy_express.domain.model.AccessToken
import com.dexciuq.yummy_express.domain.model.Authentication
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Filter
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import com.dexciuq.yummy_express.domain.model.User
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val yummyExpressApiService: YummyExpressBackendApiService,
    private val sessionManager: SessionManager,
) : DataSource.Remote {
    override suspend fun getCategoryList(): List<Category> =
        yummyExpressApiService.getAllCategories().categories.toCategory()

    override suspend fun getHomeCategoryList(): List<Category> =
        yummyExpressApiService.getAllCategories().categories.toCategory().take(6)

    override suspend fun getProductsByFilter(filter: Filter): List<Product> =
        yummyExpressApiService.getAllProducts(
            name = filter.name,
            category = filter.category?.id,
            brand = filter.brand?.joinToString(),
            page = filter.page,
            pageSize = filter.pageSize,
            sort = filter.sort,
        ).products?.fromDtoToProduct().orEmpty()

    override suspend fun getProductById(id: Long): Product =
        yummyExpressApiService.getProductById(id).product.fromDtoToProduct()

    override suspend fun getProductByUPC(upc: String): Product =
        yummyExpressApiService.getProductByUPC(upc).product.fromDtoToProduct()

    override suspend fun getFeaturedProductList(): List<Product> =
        yummyExpressApiService.getAllProducts().products?.fromDtoToProduct()?.take(8).orEmpty()

    override suspend fun login(email: String, password: String): Authentication {
        try {
            val response = yummyExpressApiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                return Authentication(tokens = response.body()?.toAuthTokens())
            }
            val message = when (response.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    "Invalid authentication credentials"
                }

                HttpURLConnection.HTTP_FORBIDDEN -> {
                    "Please, activate your account, we send message to your email"
                }

                422 -> {
                    "Please, enter valid credentials"
                }

                else -> error("Unknown status: $response")
            }
            return Authentication(message = message)
        } catch (e: HttpException) {
            return Authentication(message = "Network error")
        }
    }

    override suspend fun refresh(refreshToken: String): AccessToken =
        yummyExpressApiService.refresh(refreshToken).toAccessToken()

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        val response = yummyExpressApiService.register(
            RegisterRequest(name, surname, email, phoneNumber, password)
        )
        return response.isSuccessful
    }

    override suspend fun sendCode(email: String): Boolean {
        val response = yummyExpressApiService.sendResetCode(ResetCodeRequest(email))
        return response.isSuccessful
    }

    override suspend fun verifyCode(code: String): Boolean {
        val response = yummyExpressApiService.verifyResetCode(VerifyCodeRequest(code))
        return response.isSuccessful
    }

    override suspend fun resetPassword(resetPasswordConfig: ResetPasswordConfig): Boolean {
        val response = yummyExpressApiService.resetPassword(
            ResetPasswordRequest(
                code = resetPasswordConfig.code,
                newPassword = resetPasswordConfig.password
            )
        )
        return response.isSuccessful
    }

    override suspend fun makeOrder(order: Order) =
        yummyExpressApiService.makeOrder(
            sessionManager.getAccessToken()!!, orderRequest = OrderRequest(
                order.total,
                order.address,
                order.productList
            )
        )

    override suspend fun getOrderById(id: Long): Order {
        val response = yummyExpressApiService.getOrderById(id)
        return response.orderDto.fromDtoToDomain(response.orderItems)
    }


    override suspend fun getOrderList(): List<Order> =
        yummyExpressApiService.getOrders(sessionManager.getAccessToken()!!).orders?.toDomain()
            .orEmpty()

    override suspend fun logout(accessToken: String) =
        yummyExpressApiService.logout(accessToken)

    override suspend fun getProfileInfo(accessToken: String) =
        yummyExpressApiService.getProfileInfo(accessToken).userDto.toUser()

    override suspend fun updateUserProfile(user: User): User =
        yummyExpressApiService.updateUserById(
            user.id,
            RegisterRequest(
                firstname = user.name,
                lastname = user.surname,
                phoneNumber = user.phoneNumber,
            )
        ).userDto.toUser()
}