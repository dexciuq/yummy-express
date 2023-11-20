package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    // todo
) : DataSource.Remote {
    override suspend fun getCategoryList(): List<Category> = listOf(
        Category(
            id = 1,
            name = "Vegetables",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 2,
            name = "Fruits",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 3,
            name = "Beverages",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 4,
            name = "Grocery",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 5,
            name = "Edible oil",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 6,
            name = "Household",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        ),
        Category(
            id = 7,
            name = "Babycare",
            imageURL = "https://m.media-amazon.com/images/I/71KHvw6vsFL._AC_UF894,1000_QL80_.jpg",
            description = ""
        )
    )

    override suspend fun getFeaturedProductList(): List<Product> = listOf(

    )
}