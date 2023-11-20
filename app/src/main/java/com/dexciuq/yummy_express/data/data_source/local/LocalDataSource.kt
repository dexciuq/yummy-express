package com.dexciuq.yummy_express.data.data_source.local

import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.domain.model.OnBoarding
import javax.inject.Inject

class LocalDataSource @Inject constructor(

) : DataSource.Local {
    override suspend fun getBanners(): List<Banner> = listOf(
        Banner(id = 1, image = R.drawable.ic_banner_1),
        Banner(id = 2, image = R.drawable.ic_banner_2),
        Banner(id = 3, image = R.drawable.ic_banner_3)
    )

    override suspend fun getOnBoardingItems(): List<OnBoarding> = listOf(
        OnBoarding(
            image = R.drawable.ic_on_boarding_1,
            title = R.string.buy_grocery,
            description = R.string.lorem_ipsum,
        ),
        OnBoarding(
            image = R.drawable.ic_on_boarding_2,
            title = R.string.fast_delivery,
            description = R.string.lorem_ipsum,
        ),
        OnBoarding(
            image = R.drawable.ic_on_boarding_3,
            title = R.string.enjoy_quality_food,
            description = R.string.lorem_ipsum,
        ),
    )
}