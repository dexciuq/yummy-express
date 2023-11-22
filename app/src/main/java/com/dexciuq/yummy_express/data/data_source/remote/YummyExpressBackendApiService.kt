package com.dexciuq.yummy_express.data.data_source.remote

import com.dexciuq.yummy_express.data.model.remote.CategoriesResponse
import retrofit2.http.GET

interface YummyExpressBackendApiService {

    @GET("/v1/categories")
    suspend fun getAllCategories(): CategoriesResponse
}