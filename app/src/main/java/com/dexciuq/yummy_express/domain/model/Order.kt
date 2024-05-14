package com.dexciuq.yummy_express.domain.model

data class Order(
    val id: Long = -1,
    val total: Long,
    val address: String,
    val status: String = "",
    val createdAt: String = "",
    val deliveredAt: String = "",
    val productList: List<Product> = listOf(),
    val orderItemList: List<OrderItem> = listOf(),
)
