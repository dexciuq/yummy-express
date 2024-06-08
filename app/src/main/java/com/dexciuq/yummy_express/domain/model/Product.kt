package com.dexciuq.yummy_express.domain.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val category: Category,
    val upc: String,
    val price: Long,
    val discountPercentage: Int,
    val quantity: Long,
    val unit: String,
    val priceUnit: Double,
    val imageURL: String,
    val country: String,
    val brand: String,
    var amount: Double?,
) {
    val calculatePrice: Long = this.price / 100 * (100 - this.discountPercentage)
}
