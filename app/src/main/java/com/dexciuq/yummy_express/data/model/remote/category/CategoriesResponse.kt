package com.dexciuq.yummy_express.data.model.remote.category


import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>
)