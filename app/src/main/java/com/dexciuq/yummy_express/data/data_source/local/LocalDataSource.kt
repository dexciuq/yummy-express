package com.dexciuq.yummy_express.data.data_source.local

import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.data.data_source.DataSource
import com.dexciuq.yummy_express.domain.model.Banner
import javax.inject.Inject

class LocalDataSource @Inject constructor(

) : DataSource.Local {
    override suspend fun getBanners(): List<Banner> = listOf(
        Banner(id = 1, image = R.drawable.ic_banner_1),
        Banner(id = 2, image = R.drawable.ic_banner_2),
        Banner(id = 3, image = R.drawable.ic_banner_3)
    )
}