package com.dexciuq.yummy_express.data.data_source

import com.dexciuq.yummy_express.domain.model.AccessToken
import com.dexciuq.yummy_express.domain.model.Authentication
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Filter
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import com.dexciuq.yummy_express.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataSource {
    interface Preference {
        suspend fun setOnBoardingComplete(isCompleted: Boolean)
        suspend fun getOnBoardingComplete(): Boolean
        suspend fun getAccessToken(): String
        suspend fun setAccessToken(accessToken: String)
        suspend fun getRefreshToken(): String
        suspend fun setRefreshToken(refreshToken: String)
        suspend fun getAuthSkip(): Boolean
        suspend fun setAuthSkip(skip: Boolean)
    }

    interface Remote {
        suspend fun getCategoryList(): List<Category>
        suspend fun getFeaturedProductList(): List<Product>
        suspend fun getHomeCategoryList(): List<Category>
        suspend fun getProductsByFilter(filter: Filter): List<Product>
        suspend fun getProductsByFilterWithDiscount(filter: Filter): List<Product>
        suspend fun getProductById(id: Long): Product
        suspend fun getProductByUPC(upc: String): Product
        suspend fun login(email: String, password: String): Authentication
        suspend fun refresh(refreshToken: String): AccessToken
        suspend fun logout(accessToken: String)
        suspend fun getProfileInfo(accessToken: String): User
        suspend fun register(
            name: String,
            surname: String,
            email: String,
            phoneNumber: String,
            password: String
        ): Boolean
        suspend fun sendCode(email: String): Boolean
        suspend fun verifyCode(code: String): Boolean
        suspend fun resetPassword(resetPasswordConfig: ResetPasswordConfig): Boolean
        suspend fun makeOrder(order: Order)
        suspend fun getOrderById(id: Long): Order
        suspend fun getOrderList(): List<Order>
        suspend fun updateUserProfile(user: User): User
    }

    interface Local {
        suspend fun getBanners(): List<Banner>
        suspend fun getOnBoardingItems(): List<OnBoarding>
        fun getCartItemCount(): Flow<Int>
        fun getAllProductsFromCart(): Flow<List<Product>>
        suspend fun getCartProductById(id: Long): Product?
        suspend fun removeProductFromCart(product: Product)
        suspend fun removeAllProductFromCart()
        suspend fun addProductToCart(product: Product)
    }
}