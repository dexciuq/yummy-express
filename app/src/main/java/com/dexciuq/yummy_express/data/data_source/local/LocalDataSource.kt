package com.dexciuq.yummy_express.data.data_source.local

import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.data.data_source.local.db.ProductDao
import com.dexciuq.yummy_express.data.mapper.fromEntityToProduct
import com.dexciuq.yummy_express.data.mapper.toProductEntity
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
) : DataSource.Local {
    override suspend fun getBanners(): List<Banner> = listOf(
        Banner(id = 1, image = R.drawable.ic_banner_1),
        Banner(id = 2, image = R.drawable.ic_banner_2),
        Banner(id = 3, image = R.drawable.ic_banner_3),
        Banner(id = 4, image = R.drawable.ic_banner_4)
    )

    override suspend fun getOnBoardingItems(): List<OnBoarding> = listOf(
        OnBoarding(
            image = R.drawable.ic_on_boarding_1,
            title = R.string.buy_grocery,
            description = R.string.onboarding_description_1,
        ),
        OnBoarding(
            image = R.drawable.ic_on_boarding_2,
            title = R.string.fast_delivery,
            description = R.string.onboarding_description_2,
        ),
        OnBoarding(
            image = R.drawable.ic_on_boarding_3,
            title = R.string.enjoy_quality_food,
            description = R.string.onboarding_description_3,
        ),
    )

    override fun getCartItemCount(): Flow<Int> =
        productDao.getCartItemCount()

    override fun getAllProductsFromCart(): Flow<List<Product>> =
        productDao.getAll().map { it.fromEntityToProduct() }

    override suspend fun getCartProductById(id: Long): Product? =
        productDao.getProductById(id)?.fromEntityToProduct()

    override suspend fun addProductToCart(product: Product) =
        productDao.insert(product.toProductEntity())

    override suspend fun removeProductFromCart(product: Product) =
        productDao.delete(product.toProductEntity())

    override suspend fun removeAllProductFromCart() =
        productDao.deleteAllProducts()
}