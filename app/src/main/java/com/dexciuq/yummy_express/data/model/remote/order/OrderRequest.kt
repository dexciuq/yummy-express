package com.dexciuq.yummy_express.data.model.remote.order

import com.dexciuq.yummy_express.domain.model.Product
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("total")
    val total: Long,
    @SerializedName("address")
    val address: String,
    @SerializedName("products")
    val productList: List<Product>,
)