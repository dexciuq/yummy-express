package com.dexciuq.yummy_express.data.model.remote.order

import com.dexciuq.yummy_express.data.model.remote.product.ProductDto
import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("total")
    val total: Long?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("status_id")
    val status: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("deliveredAt")
    val deliveredAt: String? = "",
    @SerializedName("products")
    val productList: List<ProductDto>?,
)
