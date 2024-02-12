package com.dexciuq.yummy_express.data.model.remote.product


import com.dexciuq.yummy_express.data.model.remote.product.ProductDto
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product")
    val product: ProductDto
)