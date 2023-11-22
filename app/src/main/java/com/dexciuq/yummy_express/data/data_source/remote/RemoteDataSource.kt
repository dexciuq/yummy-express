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

    override suspend fun getFeaturedProductList(): List<Product> = listOf(
    )
}