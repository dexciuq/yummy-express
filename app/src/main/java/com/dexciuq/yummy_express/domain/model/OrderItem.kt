package com.dexciuq.yummy_express.domain.model

data class OrderItem(
    val id: Long,
    val productId: Long,
    val name: String,
    val price: Long,
    val description: String,
    val upc: String,
    val quantity: Long,
    val step: Double,
    val amount: Double,
    val subtotal: Long,
    val image: String,
    val category: String,
    val unit: String,
    val brand: String,
    val country: String,
)