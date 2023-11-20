package com.dexciuq.yummy_express.data.data_source

import com.dexciuq.yummy_express.domain.model.Banner

interface DataSource {
    interface Remote {

    }

    interface Local {
        suspend fun getBanners(): List<Banner>
    }
}