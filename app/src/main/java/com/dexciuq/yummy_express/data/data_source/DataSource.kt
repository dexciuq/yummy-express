package com.dexciuq.yummy_express.data.data_source

import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.model.Product

interface DataSource {
    interface Preference {
        suspend fun setOnBoardingComplete(isCompleted: Boolean)
        suspend fun getOnBoardingComplete(): Boolean
    }

    interface Remote {
        suspend fun getCategoryList(): List<Category>
        suspend fun getFeaturedProductList(): List<Product>
    }

    interface Local {
        suspend fun getBanners(): List<Banner>
        suspend fun getOnBoardingItems(): List<OnBoarding>
    }
}