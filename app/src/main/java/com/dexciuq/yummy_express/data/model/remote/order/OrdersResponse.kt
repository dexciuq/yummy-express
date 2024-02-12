package com.dexciuq.yummy_express.data.model.remote.order

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("orders")
    val orders: List<OrderDto>?
)