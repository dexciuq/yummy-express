package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.data.mapper.toCategory
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

    override suspend fun getProductsByCategory(category: Long): List<Product> {
        return when (category) {
            2L -> {
                products
            }
            else -> {
                listOf()
            }
        }
    }

    override suspend fun getFeaturedProductList(): List<Product> = products

    private val products = listOf(
        Product(
            id = 1,
            name = "Fresh Peach",
            description = "",
            category = Category(id = 55, name = "dfkj", imageURL = "", description = ""),
            upc = "45464645",
            price = 65000L,
            discountPercentage = 5,
            quantity = 5,
            unit = "kg",
            priceUnit = 1.0,
            imageURL = "https://pngfre.com/wp-content/uploads/peach-png-image-from-pngfre-33-1024x815.png",
            country = "kz",
            brand = "asi_mart"
        ),
        Product(
            id = 2,
            name = "Lemon",
            description = "",
            category = Category(id = 55, name = "dfkj", imageURL = "", description = ""),
            upc = "45464646",
            price = 25000L,
            discountPercentage = 0,
            quantity = 8,
            unit = "kg",
            priceUnit = 1.0,
            imageURL = "https://pngimg.com/d/lemon_PNG25198.png",
            country = "kz",
            brand = "asi_mart"
        )
    )
}