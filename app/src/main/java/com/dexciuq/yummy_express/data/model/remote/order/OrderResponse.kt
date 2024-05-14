package com.dexciuq.yummy_express.data.model.remote.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("order")
    val orderDto: OrderDto,
    @SerializedName("order_items")
    val orderItems: List<OrderItemDto>,
)