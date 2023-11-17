package com.dexciuq.yummy_express.domain.repository

import com.dexciuq.yummy_express.domain.model.Category
import retrofit2.Response

interface CategoryRepository {
    suspend fun getCategories(): Response<List<Category>>
}