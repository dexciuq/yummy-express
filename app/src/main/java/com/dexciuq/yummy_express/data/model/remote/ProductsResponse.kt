package com.dexciuq.yummy_express.data.model.remote


import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("metadata")
    val metadata: MetadataDto?,
    @SerializedName("products")
    val products: List<ProductDto>?
)