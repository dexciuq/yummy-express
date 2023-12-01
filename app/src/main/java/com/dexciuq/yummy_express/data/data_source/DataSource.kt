package com.dexciuq.yummy_express.data.data_source

import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface DataSource {
    interface Preference {
        suspend fun setOnBoardingComplete(isCompleted: Boolean)
        suspend fun getOnBoardingComplete(): Boolean
        suspend fun setDarkMode(isDarkMode: Boolean)
        suspend fun getDarkMode(): Boolean
        suspend fun setCurrentLanguage(language: String)
        suspend fun getCurrentLanguage(): String
        suspend fun setCurrentLanguageCode(language: String)
        suspend fun getCurrentLanguageCode(): String
    }

    interface Remote {
        suspend fun getCategoryList(): List<Category>
        suspend fun getFeaturedProductList(): List<Product>
        suspend fun getHomeCategoryList(): List<Category>
        suspend fun getProductsByCategory(category: Long): List<Product>
        suspend fun getProductById(id: Long): Product
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