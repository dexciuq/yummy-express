package com.dexciuq.yummy_express.data.model.remote.order

import com.google.gson.annotations.SerializedName

data class OrderItemDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("product_id")
    val productId: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("upc")
    val upc: String?,
    @SerializedName("quantity")
    val quantity: Long?,
    @SerializedName("step")
    val step: Double?,
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("subtotal")
    val subtotal: Long?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("country")
    val country: String?,
)