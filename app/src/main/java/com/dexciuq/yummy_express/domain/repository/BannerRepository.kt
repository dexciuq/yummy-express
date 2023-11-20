package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.Banner
import com.dexciuq.yummy_express.common.Resource
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun getBanners(): Flow<Resource<List<Banner>>>
}