package com.dexciuq.yummy_express.data.model.remote


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product")
    val product: ProductDto
)